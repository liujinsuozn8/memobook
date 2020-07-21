interface DB<T>{
    add(info:T):boolean;        // 添加
    update(info:T):boolean;     // 更新
    delete(id:string):boolean;  // 删除
    get(id:string):T|null;      // 根据id获取数据
    show():void;                // 打印所有数据
}

interface HasID{
    getID(): string;
}

class MySqlDB<T extends HasID> implements DB<T>{
    private data:{[key:string]:T};
    constructor(){
        console.log('MySqlDB is connected');
        this.data = {};
    }
    add(info: T): boolean {
        // 如果 id 不存在则添加 info，存在则跳过
        let infoID: string = info.getID();
        if (this.data.hasOwnProperty(infoID)){
            console.log("id has been existed");
            return false;
        } else {
            this.data[infoID] = info;
            console.log("info has been add");
            return true;
        }
    }
    update(info: T): boolean {
        // 如果 id 存在则更新 info
        let infoID: string = info.getID();
        if (this.data.hasOwnProperty(infoID)){
            this.data[infoID] = info;
            console.log("info has been update");
            return true;
        } else {
            console.log("id is not exist");
            return false;
        }
    }
    delete(id: string): boolean {
        // 如果 id 存在则删除
        if (this.data.hasOwnProperty(id)){
            delete this.data[id];
            console.log("info has been deleted");
            return true;
        } else {
            console.log("id is not exist");
            return false;
        }
    }
    get(id: string): T|null {
        // 如果 id 存在则返回对应的 info，不存在则返回 null
        if (this.data.hasOwnProperty(id)){
            return this.data[id];
        } else {
            return null;
        }
    }

    show():void{
        for (let k in this.data){
            console.log(`key=${k}, info=${this.data[k]}`);
        }
    }
}

class User implements HasID{
    id:string;
    username:string | undefined;
    address:string | undefined;

    constructor(){
        // 将id设为1--20的随机数
        this.id = Math.floor((Math.random() * 20 ) + 1) + '';
    }

    getID(): string{
        return this.id;
    }

    toString():string{
        return `User{name=${this.username}, address=${this.address}}`
    }
}

// 创建两个用户
let user01 = new User();
user01.username = 'testUser01';
user01.address = 'address01';

let user02 = new User();
user02.username = 'testUser02';
user02.address = 'address01';

// 创建DB实例时，固定类型
let mysqldb = new MySqlDB<User>();

// 添加数据
mysqldb.add(user01);
mysqldb.add(user02);

// 打印数据
mysqldb.show();

// 删除数据
mysqldb.delete(user01.id);
console.log(user01);

// 打印数据
mysqldb.show();

