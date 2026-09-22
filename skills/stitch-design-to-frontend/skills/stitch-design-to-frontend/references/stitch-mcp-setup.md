<<<<<<< HEAD
# Stitch MCP setup and fallback

## Goal
Enable a user to choose between a manual Stitch workflow and direct Stitch MCP use without making MCP a mandatory dependency of the skill.

## First rule: verify current setup guidance
MCP setup and authentication can change. When web access is available, consult the current official Stitch setup page before giving exact client configuration:

https://stitch.withgoogle.com/docs/mcp/setup

Google also publishes a Stitch MCP design-to-code codelab that demonstrates obtaining a Stitch API key in Stitch settings and verifying the connection by listing Stitch projects:

https://codelabs.developers.google.com/design-to-code-with-antigravity-stitch

Do not repeat outdated model-selector examples from old tutorials when they conflict with the user's current Stitch UI.

## Determine the route

### Manual route
Use immediately when:
- MCP is not configured;
- the user prefers copy/paste;
- the task is small enough that setup overhead is not worthwhile;
- the current host cannot use the needed Stitch MCP tools.

Produce a self-contained Stitch prompt and ask the user to return with the result or screenshot for review.

### MCP route
Use when:
- the host already exposes working Stitch tools;
- the user wants repeated iteration or multi-screen work;
- direct project access materially improves design-system consistency or reduces manual transfer.

Before a write operation, identify the real target project/screen. Do not invent identifiers.

## If the user wants help connecting MCP

1. **Identify the client/surface.** Determine whether the user is configuring ChatGPT desktop/Codex, Antigravity, Cursor, or another MCP-compatible client. Exact menus/config formats differ.
2. **Check current official Stitch instructions.** Prefer the official setup page above for authentication and endpoint details.
3. **Prepare authentication outside chat.** If Stitch offers an API key in the user's current account, direct the user to create/copy it in Stitch settings and store it securely. Never ask them to paste it into the conversation. If the official current method uses OAuth or another flow, follow that instead.
4. **Configure the MCP client.** Use the client's supported MCP settings or config mechanism. For Codex/ChatGPT desktop, current OpenAI documentation supports adding STDIO or Streamable HTTP MCP servers through MCP settings or Codex configuration. Use current client documentation for exact syntax rather than inventing it.
5. **Restart/reload if the client requires it.** Follow current client instructions.
6. **Verify with a read-only operation.** Ask the client/agent to list Stitch projects or another harmless read operation exposed by the actual server.
7. **Confirm tool availability.** Inspect the real tools that became available before attempting generation or editing.
8. **Fallback cleanly.** If connection fails, diagnose from the actual error and continue manually if the user wants to keep designing.

## Codex / ChatGPT desktop baseline
Current OpenAI MCP documentation states that ChatGPT desktop, Codex CLI, and the IDE extension can share Codex MCP configuration. Common supported paths include:

- ChatGPT desktop: Settings → MCP Servers → Add server; choose STDIO or Streamable HTTP; provide the current Stitch command/URL from official Stitch docs; restart as instructed.
- Codex CLI: use `codex mcp add` with the server's actual command/config, then inspect with `codex mcp list` or `/mcp`.
- Codex config: MCP servers can be represented under `[mcp_servers.<server-name>]` in `~/.codex/config.toml` or trusted project `.codex/config.toml`.

Do not put Stitch secrets directly into a skill file, plugin package, chat message, or committed repository. Use supported secret/environment handling for the user's client.

Current OpenAI MCP reference:
https://learn.chatgpt.com/docs/extend/mcp

## Security and write behavior
- Treat API keys and access tokens as secrets.
- Never include them in examples with real values.
- Do not delete or overwrite screens unless the user explicitly wants that action.
- Do not claim the connection works until a real read-only verification succeeds.
=======
# Stitch MCP setup and fallback

## Goal
Enable a user to choose between a manual Stitch workflow and direct Stitch MCP use without making MCP a mandatory dependency of the skill.

## First rule: verify current setup guidance
MCP setup and authentication can change. When web access is available, consult the current official Stitch setup page before giving exact client configuration:

https://stitch.withgoogle.com/docs/mcp/setup

Google also publishes a Stitch MCP design-to-code codelab that demonstrates obtaining a Stitch API key in Stitch settings and verifying the connection by listing Stitch projects:

https://codelabs.developers.google.com/design-to-code-with-antigravity-stitch

Do not repeat outdated model-selector examples from old tutorials when they conflict with the user's current Stitch UI.

## Determine the route

### Manual route
Use immediately when:
- MCP is not configured;
- the user prefers copy/paste;
- the task is small enough that setup overhead is not worthwhile;
- the current host cannot use the needed Stitch MCP tools.

Produce a self-contained Stitch prompt and ask the user to return with the result or screenshot for review.

### MCP route
Use when:
- the host already exposes working Stitch tools;
- the user wants repeated iteration or multi-screen work;
- direct project access materially improves design-system consistency or reduces manual transfer.

Before a write operation, identify the real target project/screen. Do not invent identifiers.

## If the user wants help connecting MCP

1. **Identify the client/surface.** Determine whether the user is configuring ChatGPT desktop/Codex, Antigravity, Cursor, or another MCP-compatible client. Exact menus/config formats differ.
2. **Check current official Stitch instructions.** Prefer the official setup page above for authentication and endpoint details.
3. **Prepare authentication outside chat.** If Stitch offers an API key in the user's current account, direct the user to create/copy it in Stitch settings and store it securely. Never ask them to paste it into the conversation. If the official current method uses OAuth or another flow, follow that instead.
4. **Configure the MCP client.** Use the client's supported MCP settings or config mechanism. For Codex/ChatGPT desktop, current OpenAI documentation supports adding STDIO or Streamable HTTP MCP servers through MCP settings or Codex configuration. Use current client documentation for exact syntax rather than inventing it.
5. **Restart/reload if the client requires it.** Follow current client instructions.
6. **Verify with a read-only operation.** Ask the client/agent to list Stitch projects or another harmless read operation exposed by the actual server.
7. **Confirm tool availability.** Inspect the real tools that became available before attempting generation or editing.
8. **Fallback cleanly.** If connection fails, diagnose from the actual error and continue manually if the user wants to keep designing.

## Codex / ChatGPT desktop baseline
Current OpenAI MCP documentation states that ChatGPT desktop, Codex CLI, and the IDE extension can share Codex MCP configuration. Common supported paths include:

- ChatGPT desktop: Settings → MCP Servers → Add server; choose STDIO or Streamable HTTP; provide the current Stitch command/URL from official Stitch docs; restart as instructed.
- Codex CLI: use `codex mcp add` with the server's actual command/config, then inspect with `codex mcp list` or `/mcp`.
- Codex config: MCP servers can be represented under `[mcp_servers.<server-name>]` in `~/.codex/config.toml` or trusted project `.codex/config.toml`.

Do not put Stitch secrets directly into a skill file, plugin package, chat message, or committed repository. Use supported secret/environment handling for the user's client.

Current OpenAI MCP reference:
https://learn.chatgpt.com/docs/extend/mcp

## Security and write behavior
- Treat API keys and access tokens as secrets.
- Never include them in examples with real values.
- Do not delete or overwrite screens unless the user explicitly wants that action.
- Do not claim the connection works until a real read-only verification succeeds.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
