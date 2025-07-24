# spring-swagger-mock-server

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![SPRING](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![SPRING BOOT](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![SWAGGER](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white) [![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)

# 概要

swagger mock server を構築し、条件に応じてresponseする機能である。

本jarは、条件に応じて、responseする機能を提供するものである.

## 処理詳細

- open api code generatorのtemplateを加工し、controller interfaceから、本機能を呼び出す.
  - swaggerで定義したstatusに応じるresponse body typeを自動生成し、本機能のmethod argumentとする.
    ```java
      Map<Integer, Class<?>> responseBodyTypes;
    ```
    - Integer: status codeを指す. 200, 400,...
    - Class<?>: statusに応じる、response body type classを指す.
  
  - swaggerで定義したrequest parameters(in header, in path, in query, request body)をMap型で自動生成し、本機能のmethod argumentとする.
    ```java
      Map<String, Object> parameters;
    ```
    - String: parameter name
    - Object: parameter value

- 以下環境変数で、設定するコンフィグファイルで、test data file格納先や、test data 特定するためのキーとなるparameterを設定する.

  ```
  CONFIG_FILE=./var/swagger/mock-server.config.json
  ```
  
  - コンフィグファイル
  
    - コンフィグファイルの各設定項目に関して
      - request_uri: context-pathを除いたuri-pathを設定する
      - request_method: request method(get, post, put, delete)を設定する
      - key_parameter_expression: spring expression languageで、Map parametersの評価式を記載する
        例：
        Map parametersが以下とする 
        ```
        {
          "apiKey": "cd9b5011-42d9-44b4-b760-b0794ae2e728",
          "id": 1,
          "company": company object(id, name, address)
        }
        ```
      
        - `apiKey`をKeyParameterとしたい場合
          ```
          ['apiKey']
          ```
        - `id`をKeyParameterとしたい場合
          ```
          ['id']
          ```
        - `company.id`をKeyParameterとしたい場合
          ```
          ['company'].id
          ```
      - delegator_class: 本機能を利用せず、個別に対応した場合、設定するクラス,
      - test_data_file_info
        - directory: test data json file 格納先directory
        - default_success_data_filename
          デフォルトの正常ケースを記載したtest data filename
          
          KeyParameter値に該当するtest dataが存在しない場合、本ファイルに記載した通りにレスポンスする
        - filename: KeyParameter毎に記載したtest data file
    - コンフィグファイルの設定例
      ```json
        [
          {
            "request_uri": "/companies",
            "request_method": "get",
            "key_parameter_expression": "['apiKey']",
            "delegator_class": "io.github.mitsumi.solutions.spring.swagger.mock.server.demo.app.domain.delegators.CompaniesApiRegisterCompanyDelegator",
            "test_data_file_info": {
              "directory": "./src/test/resources/test-data/unit-test/io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.TestDataFileLoaderTest/get-companies",
              "default_success_data_filename": "default.json",
              "filename": "test-data.json"
            }
          }
        ]
      ```
# 補足

## open api code generateする際、以下のtemplateを指定する必要がある

```
./swagger/open-api-code-generator/templates
├── api.mustache
├── apiController.mustache
└── methodBody.mustache
```

## open api code generateより、余計なファイルがうわ書かないように、以下のファイルをproject root に配置する必要がある

```
./swagger/open-api-code-generator/
└── .openapi-generator-ignore
```

## 本機能の利用したサンプリに関して、以下を参照

[spring swagger mock server demo](https://github.com/mitsumi-solutions-develop/spring-swagger-mock-server-demo)

# supported

- java version: 21

# maven dependency

```xml
  <dependency>
      <groupId>io.github.mitsumi-solutions-develop</groupId>
      <artifactId>spring-swagger-mock-server</artifactId>
      <version>1.0.0</version>
  </dependency>
```

# maven plugin

```xml
    <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>7.14.0</version> <!-- Use a recent version -->
        <executions>
            <execution>
                <goals>
                    <goal>generate</goal>
                </goals>
                <configuration>
                    <inputSpec>${project.basedir}/src/main/resources/mock-server.swagger.yaml</inputSpec>
                    <generatorName>spring</generatorName> <!-- Example: generate Java code -->
                    <output>${project.basedir}</output>
                    <apiPackage>io.github.mitsumi.solutions.spring.swagger.mock.server.demo.app.web.api.generated.controllers</apiPackage>
                    <modelPackage>io.github.mitsumi.solutions.spring.swagger.mock.server.demo.app.web.api.generated.models</modelPackage>
                    <templateDirectory>${project.basedir}/src/main/resources/swagger-api-templates</templateDirectory>
                    <generateModelTests>false</generateModelTests>
                    <generateApiTests>false</generateApiTests>
                    <configOptions>
                        <library>spring-boot</library>
                        <java8>true</java8>
                        <dateLibrary>java8</dateLibrary>
                        <serializableModel>true</serializableModel>
                        <hideGenerationTimestamp>true</hideGenerationTimestamp>
                        <useJakartaEe>true</useJakartaEe>
                    </configOptions>
                    <!-- Further configurations as needed -->
                </configuration>
            </execution>
        </executions>
    </plugin>
```
