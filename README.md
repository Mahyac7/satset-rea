# satset-rea

A tiny Android app built **on purpose** for learning reverse engineering (RE),
analyzed with [REA](https://github.com/morluto/rea) + JADX.

> This APK is our own, built from this source, so analyzing it is fully lawful.
> It's a self-made CTF-style practice target — nothing proprietary.

## What's inside

A single-screen app (`MainActivity`) that hides three "secrets" in different ways:

| # | Secret | Technique | Difficulty |
|---|--------|-----------|------------|
| 1 | `SATSET{plaintext_is_never_safe}` | Plain string field | easy |
| 2 | password `satset_2026` | Split + concatenated at runtime | medium |
| 3 | `SATSET{x0r_1s_not_crypto}` | XOR-encoded bytes, decoded at runtime | harder |

The RE challenge: recover all three by decompiling the APK — **not** by reading
this source.

## How the APK is built

GitHub Actions builds the debug APK on every push (`.github/workflows/build.yml`)
and uploads it as the `satset-rea-debug-apk` artifact. Download it from the
**Actions** tab → latest run → Artifacts.

## How we analyze it

With REA + a headless JADX engine:

```sh
rea inspect-android-package Example.apk
rea search-android-classes Example.apk MainActivity
rea inspect-android-class  Example.apk id.satset.rea.MainActivity
rea inspect-android-method Example.apk id.satset.rea.MainActivity decodeFlag
```

## For learning only

Build and inspect your own software, or software you're authorized to examine.
See REA's [DISCLAIMER](https://github.com/morluto/rea/blob/main/DISCLAIMER.md).
