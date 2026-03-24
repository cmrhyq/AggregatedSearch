<h1 align="center">Aggregated Search - 聚合搜索</h1>
<p align="center"><strong>基于 Spring Boot + Vue 3 的聚合搜索示例项目</strong></p>

## 项目简介

本项目实现了一个简易的聚合搜索平台：在同一搜索入口下，按类型聚合查询不同数据源的内容。

当前已实现的主要搜索类型：

- 文章（后端可对接 Elasticsearch）
- 用户（MySQL）
- 图片（Bing 页面抓取）

后端同时包含用户、帖子、点赞、收藏、文件上传等基础业务能力，并提供接口文档页面，便于联调和二次开发。

## 仓库结构

```text
AggregatedSearch/
├── README.md
├── aggregated-search-service/   # Spring Boot 后端
│   ├── src/main/java/com/cmrhyq/search
│   ├── src/main/resources
│   ├── sql/create_table.sql
│   └── Dockerfile
└── aggregated-search-client/    # Vue 3 + TypeScript 前端
    ├── src/
    └── package.json
```

## 技术栈

### 后端

- Java 8
- Spring Boot 2.7.2
- MyBatis + MyBatis-Plus
- Elasticsearch（可选，但推荐用于文章搜索）
- Canal（可选，用于 MySQL 增量同步到 ES）
- Redis / Spring Session（可选）
- Knife4j（接口文档）

### 前端

- Vue 3
- TypeScript
- Vue Router 4
- Axios
- Ant Design Vue 4.x

## 环境要求

建议本地准备以下环境：

- JDK 8
- Maven 3.8+
- Node.js 16+（建议）与 npm
- MySQL 8.x（或兼容版本）

可选环境（按需启用）：

- Elasticsearch（用于文章检索）
- Canal（用于 MySQL -> ES 增量同步）
- Redis（用于分布式 Session）

## 快速开始

### 1) 初始化数据库

在 MySQL 中创建数据库后，执行：

- `aggregated-search-service/sql/create_table.sql`

默认配置中的数据库名为 `aggregated_search`，可按需修改。

### 2) 配置后端

修改 `aggregated-search-service/src/main/resources/application.yml` 中的关键配置：

- `spring.datasource`
- `spring.elasticsearch`（如启用 ES）
- `canal`（如启用 Canal）
- `spring.redis` 与 `spring.session.store-type`（如启用 Redis Session）

### 3) 启动后端服务

在 `aggregated-search-service` 目录执行：

```bash
# Windows
mvnw.cmd clean package
mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw clean package
./mvnw spring-boot:run
```

默认地址：

- 服务基址：`http://localhost:8101/api`
- 接口文档：`http://localhost:8101/api/doc.html`

### 4) 启动前端

在 `aggregated-search-client` 目录执行：

```bash
npm install
npm run serve
```

常用命令：

```bash
npm run build
npm run lint
```

前端默认通过 `aggregated-search-client/src/plugins/requestAxios.ts` 请求后端：

- `http://localhost:8101/api/`

如后端地址变化，请同步修改该文件。

## 开发命令速查

### 后端（`aggregated-search-service`）

```bash
# 打包
mvnw.cmd clean package

# 本地运行
mvnw.cmd spring-boot:run

# 测试
mvnw.cmd test
```

### 前端（`aggregated-search-client`）

```bash
npm install
npm run serve
npm run build
npm run lint
```

## 可选能力说明

- **Elasticsearch**：用于文章搜索，需先根据 `sql/post_es_mapping.json` 创建索引。
- **Canal**：用于把 MySQL 增量数据同步到 ES，需先部署并配置 Canal。
- **Redis Session**：默认未启用。若需启用，参考后端子模块 `README` 调整配置与启动注解。

## 已知说明

- 仓库根目录当前只包含单个后端 Dockerfile（`aggregated-search-service/Dockerfile`），未提供 `docker-compose`。
- 根目录旧版截图路径 `MarkdownImageUpload/...` 可能在部分环境下不存在，建议后续补充到仓库内可访问目录后再引用。
- `SearchTypeEnum` 中包含 `VIDEO`，但当前前后端默认检索入口主要覆盖文章、用户、图片。

## 后续可改进方向

- 补充 `.env.example` 或配置模板，避免手动改配置时遗漏项。
- 增加一键本地依赖启动（如 `docker-compose`）以降低上手成本。
- 补充端到端测试与完整部署文档（含生产配置建议）。

