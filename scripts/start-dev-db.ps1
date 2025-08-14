# PlanbookAI Development Database Quick Setup Script
# Tu dong tao va khoi dong PostgreSQL Docker container neu chua co

Write-Host "PlanbookAI Quick Database Setup" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green

# Check Docker status
Write-Host "Checking Docker..." -ForegroundColor Yellow
try {
    docker --version | Out-Null
    Write-Host "[OK] Docker is ready" -ForegroundColor Green
} catch {
    Write-Host "[ERROR] Docker not running or not installed" -ForegroundColor Red
    Write-Host "Please install Docker Desktop first" -ForegroundColor Yellow
    Write-Host "Download: https://www.docker.com/products/docker-desktop" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Press any key to continue..." -ForegroundColor Cyan
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Check if container exists
Write-Host "Checking PostgreSQL container..." -ForegroundColor Yellow
$containerExists = docker ps -a --filter "name=planbookai-postgres-dev" --format "{{.Names}}"
if ($containerExists) {
    Write-Host "[INFO] Container exists. Checking status..." -ForegroundColor Yellow
    $containerRunning = docker ps --filter "name=planbookai-postgres-dev" --format "{{.Names}}"
    if ($containerRunning) {
        Write-Host "[OK] Database is running" -ForegroundColor Green
    } else {
        Write-Host "[INFO] Starting existing container..." -ForegroundColor Yellow
        docker start planbookai-postgres-dev
        if ($LASTEXITCODE -ne 0) {
            Write-Host "[ERROR] Failed to start container" -ForegroundColor Red
            Write-Host ""
            Write-Host "Press any key to continue..." -ForegroundColor Cyan
            $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
            exit 1
        }
    }
} else {
    Write-Host "[INFO] Creating new PostgreSQL container..." -ForegroundColor Yellow
    docker run -d --name planbookai-postgres-dev -e POSTGRES_DB=postgres -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test123 -p 5430:5432 postgres:17
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Failed to create container. Check Docker Desktop" -ForegroundColor Red
        Write-Host ""
        Write-Host "Press any key to continue..." -ForegroundColor Cyan
        $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        exit 1
    }
    Write-Host "[OK] Container created successfully" -ForegroundColor Green
}

# Wait for database to be ready
Write-Host "Waiting for database to start..." -ForegroundColor Yellow
$maxRetries = 30
$retries = 0
do {
    $healthCheck = docker exec planbookai-postgres-dev pg_isready -U test -d planbookai 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "[OK] Database is ready!" -ForegroundColor Green
        break
    }
    Start-Sleep -Seconds 1
    $retries++
    Write-Host "." -NoNewline -ForegroundColor Yellow
} while ($retries -lt $maxRetries)

if ($retries -eq $maxRetries) {
    Write-Host ""
    Write-Host "[ERROR] Database failed to start within timeout" -ForegroundColor Red
    Write-Host "Try: docker logs planbookai-postgres-dev" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Press any key to continue..." -ForegroundColor Cyan
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Check migration
Write-Host "Checking database tables..." -ForegroundColor Yellow
$migrationCheck = docker exec planbookai-postgres-dev psql -U test -d planbookai -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema NOT IN ('information_schema', 'pg_catalog');" 2>$null
if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Tables created successfully" -ForegroundColor Green
} else {
    Write-Host "[WARNING] Migration may have issues, but database is running" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "SUCCESS! PLANBOOKAI DATABASE IS READY!" -ForegroundColor Green
Write-Host "=======================================" -ForegroundColor Green
Write-Host ""
Write-Host "CONNECTION INFO:" -ForegroundColor Cyan
Write-Host "  Host/IP: localhost" -ForegroundColor White
Write-Host "  Port: 5430" -ForegroundColor White
Write-Host "  Database: planbookai" -ForegroundColor White
Write-Host "  Username: test" -ForegroundColor White
Write-Host "  Password: test123" -ForegroundColor White
Write-Host ""
Write-Host "JDBC URL FOR SPRING BOOT:" -ForegroundColor Cyan
Write-Host "  jdbc:postgresql://localhost:5430/planbookai" -ForegroundColor White
Write-Host ""
Write-Host "DATABASE SCHEMAS (DDD):" -ForegroundColor Cyan
Write-Host "  [OK] users - User Management Context" -ForegroundColor Green
Write-Host "  [OK] content - Educational Content Context" -ForegroundColor Green
Write-Host "  [OK] assessment - Assessment Context" -ForegroundColor Green
Write-Host "  [OK] students - Student Data Context" -ForegroundColor Green
Write-Host ""
Write-Host "USEFUL COMMANDS:" -ForegroundColor Yellow
Write-Host "  View logs: docker logs planbookai-postgres-dev" -ForegroundColor White
Write-Host "  Stop DB: docker stop planbookai-postgres-dev" -ForegroundColor White
Write-Host "  Restart: docker restart planbookai-postgres-dev" -ForegroundColor White
Write-Host "  Remove all: .\scripts\stop-dev-db.ps1" -ForegroundColor White
Write-Host ""
Write-Host "TIP: Check README-DEV-DATABASE.md for more details!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press any key to continue..." -ForegroundColor Cyan
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")