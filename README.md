# pixel eye

------

pixel eye 是用于简化ui验收步骤的工具;



# 管理员
```
尹铎    yinduo@meituan.com
华晓宇  huaxiaoyu@meituan.com
```

## pull request的merge，请联系
```
尹铎    yinduo@meituan.com
华晓宇  huaxiaoyu@meituan.com
```

# 分支模型
```
master: 稳定的当前线上最新版本的代码分支；禁止需求，bug修改的代码提交，只能是从stage merge到此分支；
stage: 当前正在测试或者灰度的版本；当发布版本后，会同时merge 到master和develop分支；
develop: 下一个版本的开发分支；在第一轮提测之后，会merge到stage分支；
```

# 接入方式： Gradle:

```groovy
compile('com.meituan.android.food:pxe:1.0.19')
```

If you want include with project, you can do like belows:

```groovy
compile project(':pxe')
```

in this case, you should `clone` the library of search and include it in `settings.gradle`

```groovy
include ':pxe'
project(':pxe').projectDir = new File(../pxe/library)
```
#初始化
```java
    Context context=ApplicationSingleton.getInstance();
    //exit listener
    FoodUETool.getInstance().setOnExitListener(
            context -> {
                // ...
            }
    );
    //open
    FoodUETool.getInstance().open();
    //close
    FoodUETool.getInstance().exit();

```

## 版本变化
```
1.0.19    代码优化,bug修复,支持直接切换工具子模块
```

# 其它备注

* pxe引入第三方库，请遵行：[第三方库接入方案](http://wiki.sankuai.com/pages/viewpage.action?pageId=368988821) 里的规范；
* pxe的版本号不需要手动管理，请勿手动修改`gradle.properties`;
* pxe的代码检查，请参考：[代码检查规范](http://wiki.sankuai.com/pages/viewpage.action?pageId=304156641)


License

```
search dependencies others libraries, most of them are opened in Github, so you should obey the licenses of them.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
