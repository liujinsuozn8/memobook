- 参考
    - https://git-scm.com/book/zh/v2

<span id="catalog"></span>
<span style='font-size:18px'>目录--git指令<span>

- [配置指令](#配置指令)
- [常用别名](#常用别名)
- [基本操作](#基本操作)
- [查看提交历史](#查看提交历史)
- [文件的删除](#文件的删除)
- [代码比较](#代码比较)
- [本地分支命令](#本地分支命令)
- [临时存储栈](#临时存储栈)
- [重置commit](#重置commit)
- [版本调整](#版本调整)
- [远程分支命令](#远程分支命令)
- [向远程推送一个不存在的分支](#向远程推送一个不存在的分支)
- [](#)


# 配置指令
[top](#catalog)
- `git version`, 查看 git 版本
- 3种配置
    |级别|文件路径|适用范围|指令选项|
    |-|-|-|-|
    |1|`/etc/gitconfig`|对当前系统中所有用户都适用的配置|`git config --system`|
    |2|`~/.gitconfig`|只适用于当前用户的|`git config --global`|
    |3|`.git/config`|当前项目的git目录中的配置文件，只针对当前项目有效|`git config [--local]`，默认就是`--local`|

- 配置个人用户的用户名和邮箱
    ```sh
    git config --global user.name "xxxx"
    git config --global user.email "@qq.com"
    ```

- 配置指令

    |指令|功能|
    |---|-|
    |`git config [--system/global] --list`      |查看所有配置内容|
    |`git config [--system/global] -l`          |查看所有配置内容|
    |`git config [--system/global] key "value"` |添加/更改配置|
    |`git config --global --unset "key"`        |删除配置|
    |`git config [--system/global] alias.指令别名 "指令内容，去掉开头的git"`|为复杂的指令添加别名|
    |`git config  --list |grep alias`           |查看所有已经设置的别名|

# 常用别名
[top](#catalog)

|指令|别名设置|
|--:|-|
|`git config alias.st "status"`             |检查当前暂存区状态的别名|
|`git config alias.hpic "log --oneline --graph --all"`|查看分支图|
|`git config alias.ollog "log --oneline"`   |在一行显示历史记录|
|`git config alias.endv "log -1 --pretty"`  |查看当前分支的最后一次提交对象信息|

# 基本操作
[top](#catalog)

|指令|功能|
|-|-|
|`git status`                       |检查状态|
|`git init`                         |初始化git项目|
|`git add 文件路径`                 |添加到暂存区|
|`git commit -m "提交信息"`         |提交到当前分支|
|`git commit -a -m "提交信息"`      |跳过暂存区直接提交，只能提交已跟踪的文件|
|`git mv 旧文件名 新文件名`         |暂存区文件重命名|
|`git rm 文件路径`                  |删除文件|
|`git ls-files`                     |查看暂存区中的文件列表|


# 查看提交历史
[top](#catalog)

|指令|功能|
|-|-|
|`git log`                      |显示提交历史
|`git log --pretty=oneline`     |在一行显示条历史: `SHA-1值, 提交信息`|
|`git log --oneline`            |在一行显示条历史: `SHA-1值的前几位, 提交信息`
|`git log --oneline --decorate --graph --all`   |查看详细的分支历史|
|`git reflog`                   |查看所有分支历史，包括删除的分支|


# 文件的删除
[top](#catalog)

- `git rm 文件路径`，删除指定路径下的文件
    - 撤销 `rm`
        1. `git restore --staged 文件路径`
        2. `git restore 文件路径`
- `git rm --cached 文件路径`，删除对指定文件的追踪（从暂存区删除），但是不会删除文件
    - 撤销 `rm --cached`
        - `git restore --staged 文件路径`


# 代码比较
[top](#catalog)

|指令|功能|
|-|-|
|`git diff [路径]`                  |工作区与暂存区差异比较|
|`git diff --staged [路径]`         |暂存区与版本区差异比较|
|`git diff --cached [路径]`         |暂存区与版本区差异比较|


# 本地分支命令
[top](#catalog)

|指令|功能|
|-|-|
|`git branch`|显示分支列表|
|`git branch 分支名`|创建分支|
|`git branch 分支名 提交对象的SHA-1值`|创建一个分支，并指向某个提交对象|
|`git switch 分支名`|切换分支|
|`git checkout 分支名`|切换分支|
|`git branch -d 分支名`|删除分支|
|`git branch -D 分支名`|强制删除分支|
|`git branch -v`|查看所有分支的最后一次提交|
|`git log --oneline --decorate --graph --all`|查看详细的分支历史，请参考: [常用别名](#常用别名)|
|`git merge 被合并的分支名`|将目标分支合并到<span style='color:red'>当前活动分支</span>|

# 临时存储栈
[top](#catalog)

|指令|功能|
|-|-|
|`git stash`|将已跟踪且未提交的修改保存到一个栈中，栈顶为0|
|`git stash list`|列出当前所有的存储列表|
|`git stash apply `|相当于 `git stash pop`|
|`git stash apply stash@{index}`|对当前分支应用第`index`个存储|
|`git stash drop stash@{index}`|通过存储的`index`删除某个存储|
|`git stash pop`|弹出栈顶元素，并应用到当前分支，然后将该存储删除|

# 重置commit
[top](#catalog)

|指令|功能|
|-|-|
|`git commit --amend -m 新的提交信息`           |修改最近一次提交的信息|
|`git rebase -i 重置分支的父提交对象的SHA-1值`   |重置旧版本的提交信息，在需要重置的版本前标注 `reword`|

# 版本调整
[top](#catalog)

|指令|功能|
|-|-|
|`git restore 文件名`                   |工作区 -----> 当前的暂存区，从工作区退回到暂存区|
|`git reset HEAD 文件名`                |暂存区 -----> 版本区，从暂存区退回到工作区<br>撤销 `git add`|
|`git rebase -i 变基的父对象的SHA-1值`  |合并多个提交，合并的标注`squash`，不合并的标注`pick`


# 远程分支命令
[top](#catalog)

|指令|功能|
|-|-|
|`git clone xxxx.git`                   |克隆项目|
|`git branch -a`                        |查看所有分支，包括远程分支和本地分支|
|`git branch -vv`                       |查看所有本地分支及其对应的远程分支|
|`git push origin 分支名`               |上传本地的某个分支到远程仓库的同名仓库中|
|`git push origin 远程分支名:本地分支名`  |上传本地的某个分支到远程仓库的指定分支中|
|`git push origin --all`                |上传所有分支|
|`git pull origin 分支名`               |拉取指定的远程分支，并合并到当前分支|
|`git remote add origin xxxxx.git`   |关联远程仓库|

# 向远程推送一个不存在的分支
[top](#catalog)

1. `git push origin "远程分支名:本地分支名"`，将本地分支上传到远程仓库
2. `git branch --set-upstream-to=origin/远程分支名 本地分支名`，建立远程与本地目录的联系


[top](#catalog)
