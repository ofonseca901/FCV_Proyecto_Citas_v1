<<<<<<< HEAD
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root

Write-Host "=== Preflight FCV Citas ===" -ForegroundColor Cyan

$required = @(
  ".env.example",
  "docker-compose.yml",
  "database\reference\db.sql",
  "citas-api\README.md",
  "citas-web\README.md",
  "PRD.md"
)
foreach ($f in $required) {
  if (-not (Test-Path $f)) { throw "Falta archivo requerido: $f" }
}

if (-not (Test-Path ".env")) {
  Write-Host "[AVISO] No existe .env. Copia .env.example antes de levantar Docker." -ForegroundColor Yellow
}

if (-not (Get-Command docker -ErrorAction SilentlyContinue)) { throw "Docker no está en PATH." }
docker info *> $null
if ($LASTEXITCODE -ne 0) { throw "Docker Desktop está instalado pero el engine no responde." }
docker compose version | Out-Host

foreach ($port in 3306,8080,5173,4200) {
  $used = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
  if ($used) { Write-Host "[AVISO] Puerto $port en uso." -ForegroundColor Yellow }
  else { Write-Host "[OK] Puerto $port disponible" -ForegroundColor Green }
}

Write-Host "[OK] Estructura y Docker disponibles." -ForegroundColor Green
=======
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root

Write-Host "=== Preflight FCV Citas ===" -ForegroundColor Cyan

$required = @(
  ".env.example",
  "docker-compose.yml",
  "database\reference\db.sql",
  "citas-api\README.md",
  "citas-web\README.md",
  "PRD.md"
)
foreach ($f in $required) {
  if (-not (Test-Path $f)) { throw "Falta archivo requerido: $f" }
}

if (-not (Test-Path ".env")) {
  Write-Host "[AVISO] No existe .env. Copia .env.example antes de levantar Docker." -ForegroundColor Yellow
}

if (-not (Get-Command docker -ErrorAction SilentlyContinue)) { throw "Docker no está en PATH." }
docker info *> $null
if ($LASTEXITCODE -ne 0) { throw "Docker Desktop está instalado pero el engine no responde." }
docker compose version | Out-Host

foreach ($port in 3306,8080,5173,4200) {
  $used = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
  if ($used) { Write-Host "[AVISO] Puerto $port en uso." -ForegroundColor Yellow }
  else { Write-Host "[OK] Puerto $port disponible" -ForegroundColor Green }
}

Write-Host "[OK] Estructura y Docker disponibles." -ForegroundColor Green
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
