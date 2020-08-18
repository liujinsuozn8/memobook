import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.less']
})
export class NewsComponent implements OnInit {
  public title1:string = 'history';
  public title2:string = 'pe';
  // 渲染html测试
  public htmlText = "<a href='https://www.baidu.com'>baidu</a>";

  // 属性绑定测试
  public changeText:string = 'text__deleted--bule'

  // 遍历数组测试
  public arr:string[] = ['aaa', 'bbb', 'ccc'];

  public flag:boolean = true;

  // ngSwitch 测试数据
  public switchValue:number = 3;

  // ngStyle 测试数据
  public styleObj:any = {'height':'100px', 'width':'150px', 'bc': '#bfa'};

  // 事件绑定测试
  public count:number = 0;

  // 双向数据绑定测试
  public dynamicVal:string = 'null';

  constructor() {
  }
  
  ngOnInit(): void {
    // 属性绑定测试
    // 设置一个定时器，来切换 changeText 样式
    setInterval(
      ()=>this.changeText = Math.floor(Math.random()*(1-0+1)) === 1 ? 'text__danger--red':'text__deleted--bule',
      500
    )
  }

  // 事件绑定测试
  add():void{
    this.count++;
  }
  // 事件绑定测试
  sub():void{
    this.count--;
  }

}
