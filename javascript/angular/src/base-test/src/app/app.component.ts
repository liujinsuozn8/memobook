// 引入核心模块
import { Component } from '@angular/core';

@Component({
  selector: 'app-root', // 当前组件名称
  templateUrl: './app.component.html',  // 需要使用的html
  styleUrls: ['./app.component.less'] // 需要使用的css
})
export class AppComponent {
  title = 'base-test';
}
