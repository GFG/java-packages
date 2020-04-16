# GFG Java packages
Java libs for internal usage in java projects.

# How to deploy package
In order to deploy changes, create personal access token in github and configure them as env var in your system.

### For Mac users
Edit `~/.bash_profile` and add personal token as exported env var, like:

```
export GITHUB_TOKEN="your-token"
export GITHUB_USERNAME="your-username"
```

After that go to package dir in repo, ex. `cd /path/to/repo/id-encoder` and execute next command:

```mvn clean deploy --settings /path/to/repo/settings.xml```
