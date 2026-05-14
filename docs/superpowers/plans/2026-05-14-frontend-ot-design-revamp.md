# CodeSync Next.js Migration & OT Integration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Migrate the CodeSync frontend to Next.js 15 and transform it into a premium, professional-grade collaborative editor with robust OT synchronization and a high-fidelity 'non-AI slop' design.

**Architecture:** Use Next.js App Router for high-performance routing. Implement a robust OT Client state machine (Jupiter OT) to manage local buffering and document convergence. Migrate the UI to `shadcn/ui` with custom framer-motion animations inspired by `hover.dev`.

**Tech Stack:** Next.js 15, React 19, TailwindCSS 4, shadcn/ui, framer-motion, Monaco Editor, Lucide React, Zustand.

---

### Task 1: Next.js Initialization & Core Setup

**Files:**
- Create: `frontend-next/`
- Create: `frontend-next/next.config.ts`
- Modify: `frontend-next/src/app/layout.tsx`

- [ ] **Step 1: Configure next.config.ts with proxy**
Add rewrite for `/api` and `/ws` to `localhost:8080`.
- [ ] **Step 2: Initialize shadcn/ui in Next.js**
Run: `npx shadcn@latest init --defaults` in `frontend-next/`.
- [ ] **Step 3: Port utility dependencies**
Run: `npm install tailwind-merge lucide-react framer-motion clsx tailwindcss-animate zustand axios @stomp/stompjs sockjs-client nanoid @monaco-editor/react`.
- [ ] **Step 4: Commit**
`git commit -m "chore: initialize next.js 15 and core dependencies"`

---

### Task 2: Robust OT Client & State Porting

**Files:**
- Create: `frontend-next/src/lib/ot/otClient.ts`
- Create: `frontend-next/src/store/useEditorStore.ts`

- [ ] **Step 1: Port OT Client logic**
Refactor `otClient.js` into TypeScript and ensure it handles `AwaitingAck` and `Pending` buffers.
- [ ] **Step 2: Port Zustand store**
Update store to use TypeScript and handle document metadata.
- [ ] **Step 3: Commit**
`git commit -m "feat: port OT state machine and zustand store to next.js"`

---

### Task 3: Premium 'Deep Night' Theme & Design Tokens

**Files:**
- Modify: `frontend-next/src/app/globals.css`
- Modify: `frontend-next/tailwind.config.ts`

- [ ] **Step 1: Define CSS Variables for Premium Theme**
```css
:root {
  --background: 240 10% 3.9%;
  --foreground: 0 0% 98%;
  --primary: 263.4 70% 50.4%;
  /* ... shadcn tokens ... */
}
```
- [ ] **Step 2: Implement Background Patterns (Aurora/Mesh)**
Add custom Tailwind utilities for the premium backgrounds.
- [ ] **Step 3: Commit**
`git commit -m "style: define premium design system tokens for next.js"`

---

### Task 4: High-Fidelity Editor & Collaboration Logic

**Files:**
- Create: `frontend-next/src/components/editor/EditorPane.tsx`
- Create: `frontend-next/src/app/room/[roomId]/page.tsx`

- [ ] **Step 1: Implement Editor Page using App Router**
- [ ] **Step 2: Integrate Monaco with OT Client**
Use `executeEdits` for non-destructive updates.
- [ ] **Step 3: Add Remote Presence (Cursors/Labels)**
- [ ] **Step 4: Commit**
`git commit -m "feat: implement real-time collaborative editor in next.js"`

---

### Task 5: Dashboard & Auth Revamp

**Files:**
- Create: `frontend-next/src/app/dashboard/page.tsx`
- Create: `frontend-next/src/app/login/page.tsx`

- [ ] **Step 1: Redesign Dashboard with shadcn Sidebar**
- [ ] **Step 2: Add 'Hover.dev' style transitions**
- [ ] **Step 3: Commit**
`git commit -m "style: overhaul dashboard and auth pages"`

---

### Task 6: Cleanup & Swap

- [ ] **Step 1: Verify full sync flow in Next.js environment**
- [ ] **Step 2: Replace old `frontend` folder with `frontend-next`**
- [ ] **Step 3: Commit**
`git commit -m "chore: complete next.js migration and cleanup"`
