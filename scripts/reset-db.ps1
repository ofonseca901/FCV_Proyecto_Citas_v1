<<<<<<< HEAD
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root
Write-Host "ATENCIÓN: se eliminará el volumen MySQL del laboratorio." -ForegroundColor Yellow
docker compose down -v
docker compose up -d mysql
& "$PSScriptRoot\db-smoke-test.ps1"
=======
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root
Write-Host "ATENCIÓN: se eliminará el volumen MySQL del laboratorio." -ForegroundColor Yellow
docker compose down -v
docker compose up -d mysql
& "$PSScriptRoot\db-smoke-test.ps1"
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
