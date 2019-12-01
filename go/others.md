- 参考：`https://studygolang.com/articles/22515?fr=sidebar`
- 利用管道传递来进行数值计算的各阶段解耦
    ```go
    func main() {
        for n := range sq(sq(gen(2, 3, 4))) {
            fmt.Println(n)
        }
    }

    //模拟pipline的输入stage
    func gen(args ...int) <-chan int {
        out := make(chan int)
        go func() {
            for _, n := range args {
                out <- n
            }
            close(out)
        }()
        return out
    }

    //模拟pipline的中间stage
    func sq(in <-chan int) <-chan int {
        out := make(chan int)
        go func() {
            for n := range in {
                out <- n * n
            }
            close(out)
        }()
        return out
    }
    ```

- 扇出、扇入 Fan-out，Fan-in
    - 扇出：多个函数从一个channel中读取数据，直到channel关闭
        - 一出多入
        - 利用扇出可以实现一种`分布式的工作方式`
            - 通过一组workers实现并行的CPU和IO
    - 扇入：一个函数从多个channel中读取数据，直到所有channel关闭
        - 多出一入
        - 即**将多个channel的输出合并成一个输出**(类似与`faltMap`)
    - 数值计算的改进：使用`merge`函数来做**扇入**
        ```go
        func main() {
            o1 := gen(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            s1 := sq(o1)
            s2 := sq(o1)

            for n := range merge(s1, s2) {
                fmt.Println(n)
            }
        }

        //模拟pipline的输入stage
        func gen(args ...int) <-chan int {
            out := make(chan int)
            go func() {
                for _, n := range args {
                    out <- n
                }
                close(out)
            }()
            return out
        }

        //模拟pipline的中间stage
        func sq(in <-chan int) <-chan int {
            out := make(chan int)
            go func() {
                for n := range in {
                    out <- n * n
                }
                close(out)
            }()
            return out
        }

        func merge(cs ...<-chan int) <-chan int {
            var wg sync.WaitGroup
            out := make(chan int)

            output := func(c <-chan int) {
                for n := range c {
                    out <- n
                }
                wg.Done()
            }

            wg.Add(len(cs))
            for _, c := range cs {
                go output(c)
            }

            go func() {
                wg.Wait()
                close(out)
            }()

            return out
        }

        ```
- 中途停止
    - 管道中的函数包含一个模式
        - 当数据发送完成，每个stage都应该**关闭输入channel**
        - 只要输入channel没有关闭，每个stage就会持续的接受到数据
    - 通过编写`range loop`来保证所有`goroutine`是在**所有数据已经发送到下游的时候退出**
    - 在真实场景下，每个stage都接受完channel中的所有数据是不可能的
        - 一种设计：接受方只接受数据的**部分子集**
        - 异常的处理：如果channel在上游的stage出现了错误，则当前stage应该提前退出，且接受方**不应该再等待**接受channel中的**剩余数据**
    - 主要目的是要解除channel的阻塞状态，清理内存


--------------------
func NewExcelFileFilter() PathFilter {
	return &ExcelFileFilter{}
}
func (ef *ExcelFileFilter) filter(info os.FileInfo) bool {
	// if not a file
	if !info.Mode().IsRegular() {
		return false
	}

	// check file extension
	if !strings.HasSuffix(info.Name(), ".go") {
		return false
	}

	return true
}

func ScanDir(done <-chan struct{}, dirname string, filter PathFilter) (<-chan string, <-chan error) {
	outChan := make(chan string)   //TODO
	errChan := make(chan error, 1) //TODO
	go func() {
		defer close(outChan)
		errChan <- filepath.Walk(dirname, func(path string, info os.FileInfo, err error) error {
			if err != nil {
				return err
			}

			if !filter.filter(info) {
				return nil
			}
			select {
			case outChan <- info.Name():
			case <-done:
				return errors.New("walk stop")
			}

			return nil
		})
	}()

	return outChan, errChan
}