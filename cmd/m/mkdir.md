# mkdir

make directories

---

References

- `man mkdir`

## Quickstart

```bash
mkdir dir   # Make directory
```

## Synopsis

```bash
mkdir [-pv] [-m mode] directory_name ...
```

## Options

- `-m <mode>` Set the file permission bits of the final created directory to the specified mode.
- `-p` Create intermediate directories as required.
- `-v` _Verbose output_

## Usage

```bash
mkdir -m 600 root-user-only-dir

# check
$ ls -l
drw-------   2 icehe  staff    64B Sep 30 18:25 root-user-only-dir
```

```bash
mkdir -p path/to/some-dir

# check
$ tree path
path
└── to
    └── some-dir
2 directories, 0 files
```

```bash
mkdir -p foo/bar/{app/boy/{cat,dog},egg,fool}

# check
$ tree foo
foo
└── bar
    ├── apple
    │   └── boy
    │       ├── cat
    │       └── dog
    ├── egg
    └── fool
7 directories, 0 files
```