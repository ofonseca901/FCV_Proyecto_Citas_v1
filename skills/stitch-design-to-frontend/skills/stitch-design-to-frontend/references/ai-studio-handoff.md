<<<<<<< HEAD
# Google AI Studio frontend handoff

## Purpose
Translate an explicitly approved Stitch design into a frontend implementation request without reopening design ideation.

## Current platform facts to use conservatively
Google AI Studio Build mode currently supports web applications with a React frontend by default and a Node.js server runtime. It can iterate through chat/code editing and can export generated code as a ZIP or sync with GitHub.

Official current reference:
https://ai.google.dev/gemini-api/docs/aistudio-build-mode

The existence of server/runtime features does not expand this skill's default scope. Keep the handoff frontend-oriented unless the user confirms backend or integration requirements.

## Source of truth
Compile, when available:

- approved Stitch screens/screenshots;
- DESIGN.md;
- approved content/copy;
- navigation and flow decisions;
- component states;
- responsive rules;
- accessibility requirements;
- mandatory assets;
- confirmed implementation constraints.

Do not ask AI Studio to "improve" or reinterpret the approved design.

## Handoff requirements
The implementation prompt must include:

- implementation goal and frontend scope;
- approved source-of-truth materials;
- product purpose and primary users;
- required routes/screens;
- layout/hierarchy fidelity;
- typography, color, spacing, radius, border, elevation, imagery/iconography, and motion rules;
- reusable component architecture;
- responsive behavior;
- interactions and states;
- realistic mock data strategy when backend is undefined;
- accessibility requirements;
- build/runnable quality expectations;
- acceptance criteria based on visual and behavioral fidelity.

## Default exclusions
Do not add by default:

- Firebase;
- database/storage;
- authentication;
- external APIs;
- deployment platform;
- server-side business logic;
- analytics;
- payment systems.

Add them only when explicitly requested.

## Validation request for AI Studio
Require AI Studio to inspect the live preview/build for obvious errors and compare the implementation to the attached approved design before finishing. This reduces, but does not eliminate, the need for user/skill review afterward.
=======
# Google AI Studio frontend handoff

## Purpose
Translate an explicitly approved Stitch design into a frontend implementation request without reopening design ideation.

## Current platform facts to use conservatively
Google AI Studio Build mode currently supports web applications with a React frontend by default and a Node.js server runtime. It can iterate through chat/code editing and can export generated code as a ZIP or sync with GitHub.

Official current reference:
https://ai.google.dev/gemini-api/docs/aistudio-build-mode

The existence of server/runtime features does not expand this skill's default scope. Keep the handoff frontend-oriented unless the user confirms backend or integration requirements.

## Source of truth
Compile, when available:

- approved Stitch screens/screenshots;
- DESIGN.md;
- approved content/copy;
- navigation and flow decisions;
- component states;
- responsive rules;
- accessibility requirements;
- mandatory assets;
- confirmed implementation constraints.

Do not ask AI Studio to "improve" or reinterpret the approved design.

## Handoff requirements
The implementation prompt must include:

- implementation goal and frontend scope;
- approved source-of-truth materials;
- product purpose and primary users;
- required routes/screens;
- layout/hierarchy fidelity;
- typography, color, spacing, radius, border, elevation, imagery/iconography, and motion rules;
- reusable component architecture;
- responsive behavior;
- interactions and states;
- realistic mock data strategy when backend is undefined;
- accessibility requirements;
- build/runnable quality expectations;
- acceptance criteria based on visual and behavioral fidelity.

## Default exclusions
Do not add by default:

- Firebase;
- database/storage;
- authentication;
- external APIs;
- deployment platform;
- server-side business logic;
- analytics;
- payment systems.

Add them only when explicitly requested.

## Validation request for AI Studio
Require AI Studio to inspect the live preview/build for obvious errors and compare the implementation to the attached approved design before finishing. This reduces, but does not eliminate, the need for user/skill review afterward.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
