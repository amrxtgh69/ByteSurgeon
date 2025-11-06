# Bytesurgeon

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/) 
[![License](https://img.shields.io/badge/License-APACHE-green)](LICENSE) 
[![Stars](https://img.shields.io/github/stars/amrxtgh69/ByteSurgeon?style=social)](https://github.com/amrxtgh69/ByteSurgeon)

**surgical jvm performance: observe, intervene, visualize—zero source code touched.**

tired of blind perf hunts? bytesurgeon patches bytecode for timers, hooks agents for live metrics, and flames out hotspots in grafana. from micro-tweaks to full-system observability.

## Quickstart
1. **patch a jar**: `java -jar patcher/target/bytesurgeon-patcher.jar -input myapp.jar -output patched.jar`
2. **attach agent**: `java -javaagent:agent/target/bytesurgeon-agent.jar -jar patched.jar`
3. **visualize**: metrics → prometheus → grafana (flamegraphs auto-gen).

demo app included—see [demos/simple-app](demos/simple-app).

##  Architecture (

### 1️ Core idea
observe & intervene in running java without source code:

- **observation**: time methods (perf monitoring).
- **intervention**: patch slow bits to accelerate.

jvm magic: code (methods) ≠ execution (stack). manipulate via:
- **patcher**: bytecode edits pre-run.
- **agent**: runtime hooks, no file changes.

### 2️ Patcher – how it works
goal: inject timers into .class files.

- .class = binary: header + pool + methods (code attrs w/ jvm ops).
- stack-based: insert `starttimer()` at entry, `stoptimer()` at exits (returns).
- scalable: auto-patch 1000s of methods.

### 3️ Agent – runtime hooks
- load via `-javaagent` or hot-attach.
- transform classes on-the-fly: wrap methods w/ pre/post timers.
- data: timestamp deltas → metrics sink (prometheus/micrometer).

### 4️ Metrics collection
- aggregate: times, counts, p95s.
- visualize: flamegraphs in grafana—spot time sinks.

observability = data + lens.

### 5️ Live demo flow
1. run normal → baseline.
2. patch + agent → diagnose.
3. intervene (e.g., loop patch) → reload.
4. flamegraph → victory lap.

### 6️ Scaling up
- patcher: all classes auto.
- agent: any jvm.
- metrics: microservices → architecture view.
- opt: inject fast code → instant gains.

### 7️ Why it shocks 
- beyond source: binary + live jvm mastery.
- full cycle: observe → diagnose → optimize → viz.

##  How to think 
1. jvm stack: methods → ops.
2. .class: pool + code.
3. hooks: entry/exits.
4. separate: patcher (files), agent (live), metrics (viz).

##  Setup
- java 17+, maven 3.9+.
- asm 9.5 for bytecode.
- prometheus + grafana for metrics.

build: `mvn clean install`.

##  Contribute
see [contributing.md](contributing.md). prs welcome!

##  License
mit. see [license](license).

[https://x.com/amrxtgh] | [blog post coming soon]
