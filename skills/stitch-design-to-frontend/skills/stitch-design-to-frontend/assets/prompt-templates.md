# Adaptable prompt templates

These are scaffolds. Replace every bracketed concept with actual project context before presenting a prompt. Never output placeholders.

## Initial Stitch design

```text
Design a [website / web app / mobile app] for [primary users] whose main job is to [primary user outcome].

PRODUCT CONTEXT
- Product: ...
- Primary users: ...
- Main job to be done: ...
- Primary action: ...
- Constraints: ...

INFORMATION ARCHITECTURE
Create these screens/sections in the required order:
1. ...
2. ...

LAYOUT AND HIERARCHY
- Primary focal point: ...
- Navigation model: ...
- Main workspace/hero/content frame: ...
- Supporting content hierarchy: ...

COMPONENTS AND STATES
- Core components: ...
- Include relevant default, hover, focus, active, disabled, loading, empty, error, success, validation, modal/drawer, and selected states.
- Use realistic content lengths.

VISUAL DIRECTION
- Product-grounded design thesis: ...
- Typography: ...
- Color roles: ...
- Spacing and density: ...
- Surfaces/borders/radii/elevation: ...
- Imagery/iconography: ...
- Motion: ...
- Avoid: ...

INTERACTIONS
- ...

RESPONSIVE BEHAVIOR
- Desktop: ...
- Tablet: ...
- Mobile: ...
- Explain how navigation, hierarchy, dense data, grids, and actions adapt.

ACCESSIBILITY
Maintain readable contrast, semantic hierarchy, clear labels, visible focus, keyboard-accessible controls, and touch-friendly targets.

SOURCE MATERIAL
Use the attached [approved/real source material] for [specific purpose]. Preserve [specific elements]. Do not copy unrelated branding, content, or structure.

OUTPUT INTENT
Produce a coherent high-fidelity UI direction suitable for iterative refinement in Stitch and faithful frontend implementation after approval.
```

## Stitch redesign of an existing UI

```text
Redesign the attached existing interface. Treat it as the object to transform, not merely as a mood board.

PRIMARY GOAL
Improve the interface so that [users] can [job/outcome] with stronger [hierarchy/usability/brand expression/etc.].

PRESERVE
- ...

CHANGE
- ...

STRUCTURE AND UX
- Information architecture: ...
- Navigation: ...
- Primary action: ...
- Key interaction/state requirements: ...

VISUAL DIRECTION
- Design thesis: ...
- Typography: ...
- Color roles: ...
- Spacing/density: ...
- Component surfaces: ...
- Imagery/iconography: ...
- Motion: ...
- Avoid: ...

RESPONSIVE
Preserve the intended hierarchy across desktop, tablet, and mobile. Specify how navigation, grids, dense content, and actions reflow.

ACCESSIBILITY
Maintain readable contrast, keyboard/focus behavior, clear labels, semantic structure, and touch-friendly targets.

REFERENCE RULE
Do not remove preserved content or flows unless explicitly requested. Do not introduce unrelated branding or a new product architecture.

OUTPUT INTENT
Create a high-fidelity redesign that remains faithful to the required structure while clearly solving the specified UX and visual problems.
```

## Incremental Stitch correction

```text
Refine the current Stitch design. Do not redesign the entire product.

KEEP UNCHANGED
- ...

PROBLEMS TO FIX
1. ...
2. ...

REQUIRED CHANGES
- ...

VISUAL / UX TARGET
After the changes, the interface should ...
Maintain the already approved design system, layout logic, navigation, and components unless a listed correction requires a specific change.

RESPONSIVE CHECK
Ensure the correction also works at the affected breakpoints.

DO NOT
- introduce a new visual language;
- replace approved components without cause;
- modify unrelated screens or sections.

Apply this as a focused iteration on the existing design.
```

## Google AI Studio frontend handoff

```text
Build a production-oriented frontend that faithfully implements the approved Google Stitch design provided in the attached/reference materials.

IMPLEMENTATION GOAL
Reproduce the approved UI and interactions with high visual fidelity while keeping the frontend modular, maintainable, and easy to continue developing after export.

SOURCE OF TRUTH
Treat the approved Stitch screens/screenshots, DESIGN.md, copy, assets, responsive rules, and confirmed interaction states as authoritative. Do not reinterpret the visual direction or introduce a new design system.

PRODUCT
- Purpose: ...
- Primary users: ...
- Main user journeys: ...
- Required screens/routes: ...

VISUAL FIDELITY
Reproduce the approved:
- layout and hierarchy;
- typography scale, weights, line-height, and text behavior;
- color roles/tokens;
- spacing and density;
- radii, borders, surfaces, and elevation;
- icon/image treatment;
- motion where specified;
- component states;
- responsive behavior.

COMPONENT ARCHITECTURE
Create reusable components for ...
Centralize recurring design tokens and avoid unnecessary duplication.

RESPONSIVE BEHAVIOR
Implement deliberate desktop, tablet, and mobile layouts. Preserve hierarchy while adapting navigation, grids, dense content, actions, and reading width.

INTERACTIONS AND STATES
Implement all confirmed navigation and interactions plus relevant hover, focus, active, disabled, loading, empty, error, success, validation, modal/drawer, and form states.

DATA
If real backend/API requirements are not provided, use realistic mock data and isolate the data layer so it can be replaced later. Do not invent production integrations.

ACCESSIBILITY
Use semantic structure, keyboard-accessible controls, visible focus states, appropriate labels, readable contrast, and accessible form behavior.

ENGINEERING QUALITY
- Keep the project runnable.
- Use clear component boundaries.
- Avoid unnecessary dependencies.
- Keep secrets out of client-side code.
- Preserve a structure suitable for export/download and continued development.

DO NOT ADD UNLESS EXPLICITLY REQUESTED
- Firebase;
- database/storage;
- authentication;
- deployment configuration;
- unrelated backend services.

ACCEPTANCE CRITERIA
1. The running frontend visually matches the approved Stitch design.
2. All required screens/routes are present.
3. Primary interactions and UI states work.
4. Responsive layouts preserve the approved hierarchy.
5. The code is modular and maintainable.
6. No unrequested backend/integration scope was introduced.
7. The project can be exported and continued in another development environment.

Before finishing, compare the live preview to the approved reference, fix obvious fidelity/build/runtime problems, and verify these acceptance criteria.
```

## Google AI Studio corrective implementation prompt

```text
Refine the existing implementation. Do not redesign or regenerate the application from scratch.

SOURCE OF TRUTH
The approved Stitch design remains authoritative.

KEEP UNCHANGED
- ...

MISMATCHES TO FIX
1. ...
2. ...

REQUIRED CODE / UI CHANGES
- ...

RESPONSIVE AND STATE CHECK
Verify the corrected implementation at the affected breakpoints and interaction states.

DO NOT
- change unrelated components or routes;
- introduce a new design system;
- replace already-correct implementation choices without need.

After the changes, run/inspect the live preview and confirm that the listed mismatches are resolved without regressions.
```
