# 実行する方法

1. 環境変数に App Insights の接続文字列を設定する

```
export APPLICATIONINSIGHTS_CONNECTION_STRING="InstrumentationKey=xxxxxxxxxxxxxxx;IngestionEndpoint=https://japaneast-1.in.applicationinsights.azure.com/;LiveEndpoint=https://japaneast.livediagnostics.monitor.azure.com/"
```

2. maven の実行

```
cd demo
./mvnw spring-boot:run
```

# デプロイする方法

1. パッケージの作成


```
make build
```

2. Docker イメージの作成とプッシュ

必要に応じて、Makefile のタグを変更する

```
make docker-build IMAGE_NAME=tsubasaxzzz/demo-spring-openjdkms TAG=0.1
make docker-push IMAGE_NAME=tsubasaxzzz/demo-spring-openjdkms TAG=0.1
```

3. k8s の YAML ファイルの生成

```
make build-k8sdeploy-yaml AI_CONNECTION_STRING="InstrumentationKey=xxxxxxxxxxxxx;IngestionEndpoint=https://japaneast-1.in.applicationinsights.azure.com/;LiveEndpoint=https://japaneast.livediagnostics.monitor.azure.com/" IMAGE_NAME=tsubasaxzzz/demo-spring-openjdkms TAG=0.1
```

4. (Option) まとめて実行する方法

```
 make build-push AI_CONNECTION_STRING="InstrumentationKey=xxxxxxxx;IngestionEndpoint=https://japaneast-1.in.applicationinsights.azure.com/;LiveEndpoint=https://japaneast.livediagnostics.monitor.azure.com/" IMAGE_NAME=tsubasaxzzz/demo-spring-openjdkms TAG=0.2
 ``````

# ACR タスクを使ってビルドする方法

```bash
ACR_NAME=<acrname>
GIT_PAT=<gitpat>

# ACR タスクの作成
az acr task create \
--registry $ACR_NAME \
--name tasksamplespring \
--image sample-spring:{{.Run.ID}} \
-context "https://github.com/tsubasaxZZZ/sample-springboot-with-appinsights#main"
--file Dockerfile 
--git-access-token $GIT_PAT

# テストする
az acr task run --registry $ACR_NAME --name tasksamplespring
```
