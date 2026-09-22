<<<<<<< HEAD
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root

if (-not (Test-Path ".env")) { throw "Falta .env" }
$envMap = @{}
Get-Content .env | Where-Object { $_ -match '^[A-Za-z_][A-Za-z0-9_]*=' } | ForEach-Object {
  $k,$v = $_.Split('=',2); $envMap[$k] = $v
}
$db = $envMap['MYSQL_DATABASE']
$rootPassword = $envMap['MYSQL_ROOT_PASSWORD']

Write-Host "Esperando MySQL..." -ForegroundColor Cyan
for ($i=0; $i -lt 30; $i++) {
  docker compose exec -T mysql mysqladmin ping -h 127.0.0.1 -uroot -p$rootPassword --silent *> $null
  if ($LASTEXITCODE -eq 0) { break }
  Start-Sleep -Seconds 2
}

$sql = @"
USE $db;
SELECT COUNT(*) AS tables_count FROM information_schema.tables WHERE table_schema='$db';
SELECT COUNT(*) AS locations_count FROM locations;
SELECT COUNT(*) AS specialties_count FROM specialties;
SELECT COUNT(*) AS users_count FROM users;
SELECT COUNT(*) AS professionals_count FROM professionals;
SELECT COUNT(*) AS appointments_count FROM appointments;
"@

$sql | docker compose exec -T mysql mysql -uroot -p$rootPassword
if ($LASTEXITCODE -ne 0) { throw "Smoke test DB falló" }
Write-Host "[OK] db.sql cargado y consultable." -ForegroundColor Green
=======
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root

if (-not (Test-Path ".env")) { throw "Falta .env" }
$envMap = @{}
Get-Content .env | Where-Object { $_ -match '^[A-Za-z_][A-Za-z0-9_]*=' } | ForEach-Object {
  $k,$v = $_.Split('=',2); $envMap[$k] = $v
}
$db = $envMap['MYSQL_DATABASE']
$rootPassword = $envMap['MYSQL_ROOT_PASSWORD']

Write-Host "Esperando MySQL..." -ForegroundColor Cyan
for ($i=0; $i -lt 30; $i++) {
  docker compose exec -T mysql mysqladmin ping -h 127.0.0.1 -uroot -p$rootPassword --silent *> $null
  if ($LASTEXITCODE -eq 0) { break }
  Start-Sleep -Seconds 2
}

$sql = @"
USE $db;
SELECT COUNT(*) AS tables_count FROM information_schema.tables WHERE table_schema='$db';
SELECT COUNT(*) AS locations_count FROM locations;
SELECT COUNT(*) AS specialties_count FROM specialties;
SELECT COUNT(*) AS users_count FROM users;
SELECT COUNT(*) AS professionals_count FROM professionals;
SELECT COUNT(*) AS appointments_count FROM appointments;
"@

$sql | docker compose exec -T mysql mysql -uroot -p$rootPassword
if ($LASTEXITCODE -ne 0) { throw "Smoke test DB falló" }
Write-Host "[OK] db.sql cargado y consultable." -ForegroundColor Green
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
