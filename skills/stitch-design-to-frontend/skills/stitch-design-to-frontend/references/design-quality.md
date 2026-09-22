<<<<<<< HEAD
# Design quality rules

## Product-grounded direction
Derive the visual language from the product, audience, task, content, and brand constraints. Do not default every product to the same SaaS dashboard, centered hero, card grid, purple gradient, glass effect, or generic geometric sans-serif treatment.

Choose a coherent design thesis before specifying decoration. Examples of useful dimensions include editorial vs. utilitarian, quiet vs. expressive, dense vs. spacious, formal vs. playful, and static vs. motion-led. Use only the dimensions justified by the brief.

## Hierarchy and composition
- Make the primary task visually obvious.
- Create one clear focal hierarchy per screen.
- Use asymmetry, whitespace, grids, or dense composition intentionally rather than as a stylistic reflex.
- Keep secondary actions subordinate.
- Avoid visual competition between headings, metrics, cards, and calls to action.
- Match information density to the user's real decision-making context.

## Typography
- Choose typography deliberately for the subject and reading conditions.
- Define a coherent scale, weight hierarchy, line-height, and spacing behavior.
- Avoid choosing a typeface merely because it is a common AI/default option; a common typeface is acceptable when it is genuinely appropriate.
- Keep long-form line lengths readable and ensure labels remain legible at compact sizes.
- Use text as a compositional element only when it helps the product's identity and task.

## Color and surfaces
- Define semantic color roles instead of a loose palette: background, surface, primary text, secondary text, accent/action, borders, success, warning, danger, focus.
- Use sufficient contrast and do not rely on color alone to communicate state.
- Use gradients, glassmorphism, heavy shadows, neon, or texture only when the product concept supports them.
- Keep border, radius, elevation, and surface rules consistent across the interface.

## Components and states
Specify component behavior, not only static appearance. Include relevant:

- default;
- hover;
- focus;
- active/selected;
- disabled;
- loading;
- empty;
- error;
- success;
- validation;
- modal/drawer/menu open states.

Do not create a large component taxonomy when the product does not need it.

## Content realism
Use plausible labels and realistic text lengths. Avoid fabricated business metrics, claims, testimonials, user counts, or legal/commercial facts unless the user provides them. Placeholder data should look structurally realistic without masquerading as factual evidence.

## Responsive behavior
Describe how hierarchy changes, not just how columns stack.

Consider:
- navigation transformation;
- priority of content and actions;
- table/data alternatives;
- card/grid reflow;
- reading width;
- touch targets;
- sticky or persistent actions;
- safe handling of long labels and translated text.

## Accessibility
Require readable contrast, semantic hierarchy, keyboard-accessible controls, visible focus, clear labels, sufficient target sizes, and state communication that does not depend on color alone.

## Motion
Use motion to clarify state, hierarchy, continuity, or feedback. Prefer a few purposeful transitions to decorative movement everywhere. Respect reduced-motion expectations when motion is part of the product.

## Design-system continuity
When multiple screens share a visual language, keep reusable rules explicit. Use DESIGN.md when available or when the project's size makes a portable source of truth valuable. Do not silently replace the user's established system with a new one.
=======
# Design quality rules

## Product-grounded direction
Derive the visual language from the product, audience, task, content, and brand constraints. Do not default every product to the same SaaS dashboard, centered hero, card grid, purple gradient, glass effect, or generic geometric sans-serif treatment.

Choose a coherent design thesis before specifying decoration. Examples of useful dimensions include editorial vs. utilitarian, quiet vs. expressive, dense vs. spacious, formal vs. playful, and static vs. motion-led. Use only the dimensions justified by the brief.

## Hierarchy and composition
- Make the primary task visually obvious.
- Create one clear focal hierarchy per screen.
- Use asymmetry, whitespace, grids, or dense composition intentionally rather than as a stylistic reflex.
- Keep secondary actions subordinate.
- Avoid visual competition between headings, metrics, cards, and calls to action.
- Match information density to the user's real decision-making context.

## Typography
- Choose typography deliberately for the subject and reading conditions.
- Define a coherent scale, weight hierarchy, line-height, and spacing behavior.
- Avoid choosing a typeface merely because it is a common AI/default option; a common typeface is acceptable when it is genuinely appropriate.
- Keep long-form line lengths readable and ensure labels remain legible at compact sizes.
- Use text as a compositional element only when it helps the product's identity and task.

## Color and surfaces
- Define semantic color roles instead of a loose palette: background, surface, primary text, secondary text, accent/action, borders, success, warning, danger, focus.
- Use sufficient contrast and do not rely on color alone to communicate state.
- Use gradients, glassmorphism, heavy shadows, neon, or texture only when the product concept supports them.
- Keep border, radius, elevation, and surface rules consistent across the interface.

## Components and states
Specify component behavior, not only static appearance. Include relevant:

- default;
- hover;
- focus;
- active/selected;
- disabled;
- loading;
- empty;
- error;
- success;
- validation;
- modal/drawer/menu open states.

Do not create a large component taxonomy when the product does not need it.

## Content realism
Use plausible labels and realistic text lengths. Avoid fabricated business metrics, claims, testimonials, user counts, or legal/commercial facts unless the user provides them. Placeholder data should look structurally realistic without masquerading as factual evidence.

## Responsive behavior
Describe how hierarchy changes, not just how columns stack.

Consider:
- navigation transformation;
- priority of content and actions;
- table/data alternatives;
- card/grid reflow;
- reading width;
- touch targets;
- sticky or persistent actions;
- safe handling of long labels and translated text.

## Accessibility
Require readable contrast, semantic hierarchy, keyboard-accessible controls, visible focus, clear labels, sufficient target sizes, and state communication that does not depend on color alone.

## Motion
Use motion to clarify state, hierarchy, continuity, or feedback. Prefer a few purposeful transitions to decorative movement everywhere. Respect reduced-motion expectations when motion is part of the product.

## Design-system continuity
When multiple screens share a visual language, keep reusable rules explicit. Use DESIGN.md when available or when the project's size makes a portable source of truth valuable. Do not silently replace the user's established system with a new one.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
