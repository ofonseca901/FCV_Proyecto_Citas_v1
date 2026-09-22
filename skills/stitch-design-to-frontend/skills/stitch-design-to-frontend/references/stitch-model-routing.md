# Stitch model routing

## Purpose
Choose one current Stitch model mode based on the user's task rather than by habit.

## Current selector captured in the source material
As of the conversion context dated 2026-09-10, Stitch exposes:

- **Velocidad / Gemini 3.5**: optimize for speed.
- **Equilibrado / Gemini 3.8**: balance speed with higher-quality reasoning/design output.

Treat these as current UI labels supplied by the source project, not stable API identifiers. If the UI changes, verify the current naming before giving exact selector instructions.

## Recommend Velocidad / Gemini 3.5 when
- generating early visual exploration where several directions may be discarded;
- testing a small variant of an established screen;
- changing copy, spacing, one component, or another low-risk local detail;
- iterating rapidly after the structural design is already sound;
- the user explicitly prioritizes turnaround speed over deeper design reasoning.

## Recommend Equilibrado / Gemini 3.8 when
- creating the first serious design direction for a complex product;
- solving navigation, hierarchy, or information-architecture problems;
- working across multiple screens or flows that must stay coherent;
- synthesizing or applying a design system;
- transforming an existing interface with high preservation/fidelity requirements;
- resolving a systemic visual problem;
- preparing the final refinement before approval;
- the user explicitly prioritizes design quality or fidelity.

## Switching during the workflow
A project does not need one mode forever. Typical pattern:

1. Use **Equilibrado / Gemini 3.8** for the initial high-consequence composition.
2. Use **Velocidad / Gemini 3.5** for local refinements and quick variants.
3. Return to **Equilibrado / Gemini 3.8** for a difficult systemic correction or final polish.

Always state the recommended mode briefly. Do not pretend the MCP can set the mode unless the live tool schema actually exposes a supported parameter for it.
