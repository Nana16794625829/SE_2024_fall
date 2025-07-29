#!/bin/bash

# build-backend.sh - 最簡化版本
echo "🏗️ 構建後端..."

# 獲取目錄路徑
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR/../SE-form-backend"

# 進入後端目錄
cd "$BACKEND_DIR"

# Maven 構建
echo "📦 Maven 構建中..."
mvn clean package -DskipTests

# 檢查構建結果
if [ -f target/*.jar ]; then
    echo "✅ 後端構建完成"
    echo "📄 JAR 文件："
    ls -la target/*.jar
else
    echo "❌ 後端構建失敗"
    exit 1
fi

echo "🎉 完成！"