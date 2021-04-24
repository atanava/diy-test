### Реальная задача из проекта (очищенная от деталей)

#### Реализовать `ViewModelsConverter#toRootModels`  
Сюда приходит список из UI: `ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL, [ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL], ...`
Или:
```
+ROOT
++TECHNOLOGY
+++MATERIAL
+++MATERIAL
+++MATERIAL
++TECHNOLOGY
+++MATERIAL
+++MATERIAL
+++MATERIAL
+ROOT
++TECHNOLOGY
+++MATERIAL
+++MATERIAL
+++MATERIAL
++TECHNOLOGY
+++MATERIAL
+++MATERIAL
+++MATERIAL
...
```

Проверки на правильный порядок нужны

Рефакторить `ViewModelsConverter#toTechModel` можно, но смысл менять нельзя. Этот метод используется в проекте отдельно.

Решение - ссылку на вашу реализацию **класса `ViewModelsConverter` + ваш email** в JavaOps присылать в:   
Skype: grigory.kislin / Telegram: @gkislin

