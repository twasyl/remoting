/*
 * While this is not a plugin, it is much simpler to reuse the pipeline code for CI. This allows for
 * easy Linux/Windows testing and produces incrementals. The only feature that relates to plugins is
 * allowing one to test against multiple Jenkins versions.
 */
buildPlugin(timeout: 15, useContainerAgent: true, configurations: [
  [ platform: 'linux', jdk: '11' ],
  [ platform: 'windows', jdk: '11' ],
  [ platform: 'linux', jdk: '17' ],
])
