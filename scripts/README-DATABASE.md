# PlanbookAI Database Setup

## 1. Start Database

Chuột phải: `scripts/start-dev-db.ps1` chọn "Run with PowerShell"

### 2. Create Database Structure

Chuột phải: `scripts/db-migration.ps1` chọn "Run with PowerShell"

### 3. Stop Database

Chuột phải: `scripts/stop-dev-db.ps1` chọn "Run with PowerShell"

## Connection Info

- Host: localhost
- Port: 5430
- Database: planbookai
- Username: test
- Password: test123

## HƯỚNG DẪN CHẠY DỰ ÁN (THEO DOCUMENT)

### TRÌNH TỰ CHẠY ĐÚNG ĐỂ DỰ ÁN HOẠT ĐỘNG:

#### BƯỚC 1: KHỞI ĐỘNG DATABASE (BẮT BUỘC)
```powershell
# Luôn chạy đầu tiên
.\scripts\start-dev-db.ps1
```

#### BƯỚC 2: TẠO DATABASE STRUCTURE (LẦN ĐẦU)
```powershell
# Chỉ cần chạy 1 lần khi setup lần đầu
.\scripts\db-migration.ps1
```

#### BƯỚC 3: THÊM DATA CẦN THIẾT (LẦN ĐẦU)
```powershell
# User data (roles + admin user) - từ Documents/init-db.sql
docker cp Documents/init-db.sql planbookai-postgres-dev:/tmp/init-db.sql
docker exec planbookai-postgres-dev psql -U test -d planbookai -f /tmp/init-db.sql

# Sample data cho Plan & Task Service
docker cp scripts/plan-task-sample-data.sql planbookai-postgres-dev:/tmp/plan-task-sample-data.sql
docker exec planbookai-postgres-dev psql -U test -d planbookai -f /tmp/plan-task-sample-data.sql
```

#### BƯỚC 4: CHẠY SERVICES (MỖI LẦN DEVELOPMENT)
```bash
# Terminal riêng cho từng service
cd src/plan-service && mvn spring-boot:run    # Port 8083
cd src/task-service && mvn spring-boot:run    # Port 8084
cd src/auth-service && mvn spring-boot:run    # Port 8081 (nếu cần)
cd src/gateway-service && mvn spring-boot:run # Port 8080 (nếu cần)
```

### DATA DEPENDENCIES (THEO DOCUMENT):

**1. Essential Data (PHẢI CÓ):**
- users.roles (4 roles)
- users.users (admin user) 

**2. Plan Service Data:**
- content.lesson_templates (templates để tạo lesson plans)
- content.lesson_plans (sample lesson plans)

**3. Task Service Data:**
- assessment.questions + question_choices (ngân hàng câu hỏi)
- assessment.exams + exam_questions (đề thi mẫu)
- students.classes + students.students (dữ liệu học sinh)

### KẾT LUẬN:
Mỗi lần chạy dự án chỉ cần: Database UP → Services UP → Code!