<<<<<<< HEAD
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot

foreach ($repo in @("citas-api", "citas-web")) {
  $path = Join-Path $Root $repo
  Write-Host "=== $repo ===" -ForegroundColor Cyan
  if (-not (Test-Path (Join-Path $path ".git"))) {
    git -C $path init -b main
    git -C $path add .
    git -C $path commit -m "chore: initialize training template"
    git -C $path switch -c develop
    Write-Host "[OK] main + develop creadas" -ForegroundColor Green
  } else {
    Write-Host "[SKIP] ya existe .git" -ForegroundColor Yellow
  }
}
=======
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot

foreach ($repo in @("citas-api", "citas-web")) {
  $path = Join-Path $Root $repo
  Write-Host "=== $repo ===" -ForegroundColor Cyan
  if (-not (Test-Path (Join-Path $path ".git"))) {
    git -C $path init -b main
    git -C $path add .
    git -C $path commit -m "chore: initialize training template"
    git -C $path switch -c develop
    Write-Host "[OK] main + develop creadas" -ForegroundColor Green
  } else {
    Write-Host "[SKIP] ya existe .git" -ForegroundColor Yellow
  }
}
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
