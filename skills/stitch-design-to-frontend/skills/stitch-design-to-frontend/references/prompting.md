<<<<<<< HEAD
# Prompting for Google Stitch

## Prompt enhancement pipeline
Before sending or executing a design prompt:

1. Identify the product, user, main job, and platform.
2. Establish the screen/flow scope.
3. Set the information hierarchy and primary action.
4. Choose a subject-grounded visual direction.
5. Specify observable design rules: typography, color roles, spacing/density, surfaces, imagery/iconography, motion.
6. Specify components and relevant interaction states.
7. Specify responsive behavior.
8. State what source material to preserve and what not to copy.
9. Remove vague adjectives that do not map to concrete design decisions.
10. Check for contradictions between copy, layout, and user flow.

## Prompt shape
A strong Stitch prompt generally covers:

- PRODUCT CONTEXT
- USERS AND PRIMARY JOB
- SCREENS / INFORMATION ARCHITECTURE
- LAYOUT AND HIERARCHY
- COMPONENTS AND STATES
- VISUAL DIRECTION
- INTERACTIONS
- RESPONSIVE BEHAVIOR
- ACCESSIBILITY
- SOURCE MATERIAL / PRESERVATION RULES
- AVOID
- OUTPUT INTENT

The order can change when that improves clarity.

## Existing interface vs. inspiration
If the user wants to transform an existing UI, say explicitly which structure, content, navigation, or brand elements must survive. If a screenshot is only inspiration, identify the qualities to borrow—such as density, typographic contrast, image treatment, or composition—and prohibit copying unrelated brand/content.

## DESIGN.md
When a DESIGN.md is present, read it before prompting and translate its tokens/rules into the generation request where relevant. Do not duplicate the entire file in every prompt. Reference the authoritative rules and call out only the high-impact constraints for the current screen.

When no DESIGN.md exists but the project spans multiple screens, consider creating one from confirmed design decisions or real Stitch design evidence.

## Corrective prompts
A correction must be incremental:

- list what stays unchanged;
- identify concrete failures;
- specify exact changes and locations;
- state the intended outcome;
- include responsive implications;
- prohibit introducing a new design language unless the user asked for a redesign.

Avoid open-ended instructions such as "improve everything" or "make it nicer".

## Current official guidance
When web access is available and the task depends on exact or recently changed Stitch behavior, consult the current Stitch Effective Prompting Guide:

https://stitch.withgoogle.com/docs/learn/prompting/

Current official guidance should override stale platform-specific tactics in this reference, while the user's confirmed product requirements remain authoritative for the design itself.
=======
# Prompting for Google Stitch

## Prompt enhancement pipeline
Before sending or executing a design prompt:

1. Identify the product, user, main job, and platform.
2. Establish the screen/flow scope.
3. Set the information hierarchy and primary action.
4. Choose a subject-grounded visual direction.
5. Specify observable design rules: typography, color roles, spacing/density, surfaces, imagery/iconography, motion.
6. Specify components and relevant interaction states.
7. Specify responsive behavior.
8. State what source material to preserve and what not to copy.
9. Remove vague adjectives that do not map to concrete design decisions.
10. Check for contradictions between copy, layout, and user flow.

## Prompt shape
A strong Stitch prompt generally covers:

- PRODUCT CONTEXT
- USERS AND PRIMARY JOB
- SCREENS / INFORMATION ARCHITECTURE
- LAYOUT AND HIERARCHY
- COMPONENTS AND STATES
- VISUAL DIRECTION
- INTERACTIONS
- RESPONSIVE BEHAVIOR
- ACCESSIBILITY
- SOURCE MATERIAL / PRESERVATION RULES
- AVOID
- OUTPUT INTENT

The order can change when that improves clarity.

## Existing interface vs. inspiration
If the user wants to transform an existing UI, say explicitly which structure, content, navigation, or brand elements must survive. If a screenshot is only inspiration, identify the qualities to borrow—such as density, typographic contrast, image treatment, or composition—and prohibit copying unrelated brand/content.

## DESIGN.md
When a DESIGN.md is present, read it before prompting and translate its tokens/rules into the generation request where relevant. Do not duplicate the entire file in every prompt. Reference the authoritative rules and call out only the high-impact constraints for the current screen.

When no DESIGN.md exists but the project spans multiple screens, consider creating one from confirmed design decisions or real Stitch design evidence.

## Corrective prompts
A correction must be incremental:

- list what stays unchanged;
- identify concrete failures;
- specify exact changes and locations;
- state the intended outcome;
- include responsive implications;
- prohibit introducing a new design language unless the user asked for a redesign.

Avoid open-ended instructions such as "improve everything" or "make it nicer".

## Current official guidance
When web access is available and the task depends on exact or recently changed Stitch behavior, consult the current Stitch Effective Prompting Guide:

https://stitch.withgoogle.com/docs/learn/prompting/

Current official guidance should override stale platform-specific tactics in this reference, while the user's confirmed product requirements remain authoritative for the design itself.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
