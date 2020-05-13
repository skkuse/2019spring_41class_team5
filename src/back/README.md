# Dealistic Backend

Developed with Intellij IDEA

## IMPORTANT NOTE

You must install correct version of `chromedriver` before running the application.

Please replace `src/main/resources/chromedriver.exe` with proper binary file. be careful with operating system and extension.

Included Chrome Driver Version: Windows, 81

See: https://chromedriver.chromium.org/downloads

## Application Run Parameters

`src/main/resources/application.properties`

```properties
# Run Configuration: specify your os (windows or linux)
environment.os=windows
# Crawler Enable: if you want to turn off crawler feature, set to false. if false, chromedriver is not needed.
debug.crawler.enable=true
# Crawler Debug: if you want to see what crawler actually doing, set to false.
debug.crawler.headless=true
# Crawler limit: set limit of crawling review page.
debug.crawler.maxpage=10
```
