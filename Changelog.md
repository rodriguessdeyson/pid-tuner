# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-10-04

### Added

- Z&N, CHR, CC, IMC, IAE/ITAE, Tyreus-Luyben tests.
- Transfer Function (Model and Controller) rendering with [Katex](https://katex.org/).
- Step response plot with [Apache Echarts](https://www.apache.org/licenses/LICENSE-2.0).

### Changed
- New layout.
- New help section through the app.

### Fixed
- Fixed a bug that caused all the results for Servo and Regulator computations to be identical.
- Resolved an issue with the incorrect PID model in IMC.

### Removed
- Reduced app size by removing many image assets.

[1.0.0]: https://github.com/rodriguessdeyson/pid-tuner/compare/main...pid-tuner-v1

## [1.0.0] - 2022-09-04

### Added

- Z&N tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).
- CHR tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).
- CC tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).
- IMC tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).
- IAE/ITAE tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).
- Tyreus-Luyben tuningModel method by [@rodriguessdeyson](https://github.com/rodriguessdeyson).

[1.0.0]: https://github.com/rodriguessdeyson/pid-tuner/compare/main...pid-tuner-v1