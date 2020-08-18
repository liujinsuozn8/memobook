// 浏览器解析模块
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms'
// Angular核心模块
import { NgModule } from '@angular/core';
// 根组件
import { AppComponent } from './app.component';
import { NewsComponent } from './components/news/news.component';

// 装饰器接收一个元数据对象，告诉angular如何编译和启动应用
@NgModule({
  declarations: [ // 配置当前项目的组件
    AppComponent, NewsComponent
  ],
  imports: [  // 配置当前模块运行依赖的其他模块
    BrowserModule, FormsModule
  ],
  providers: [],  // 配置项目所需要的服务
  // 指定应用的主视图，即根组件
  // 通过引导根组件来启动应用
  bootstrap: [AppComponent]
})

// 根模块不需要导出任何东西，因为其他组件不需要导入模块
export class AppModule { }
