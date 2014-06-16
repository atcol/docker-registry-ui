package docker.registry.web.support

class ContainerConfig {
    String hostname
    String user
    int memory
    int memorySwap
    boolean attachStdin
    boolean attachStdout
    boolean attachStderr
    String portSpecs
    boolean tty
    boolean openStdin
    boolean stdinOnce
    String env
    List<String> command
    String dns
    String image
    List<String> volumes
    String volumesFrom
    String size

//        container_config: {
//            Hostname: "host-test",
//            User: "",
//            Memory: 0,
//            MemorySwap: 0,
//            AttachStdin: false,
//            AttachStdout: false,
//            AttachStderr: false,
//            PortSpecs: null,
//            Tty: false,
//            OpenStdin: false,
//            StdinOnce: false,
//            Env: null,
//            Cmd: [
//            "/bin/bash",
//            "-c",
//            "apt-get -q -yy -f install libevent-dev"
//            ],
//            Dns: null,
//            Image: "imagename/blah",
//            Volumes: { },
//            VolumesFrom: ""
//        },
}
