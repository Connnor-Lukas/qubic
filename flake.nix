{
  description = "Java Devlopment Shell";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

  outputs = {nixpkgs, ...}: let
    supportedSystems = [
      "aarch64-darwin"
      "aarch64-linux"
      "x86_64-linux"
    ];

    forAllSystems = nixpkgs.lib.genAttrs supportedSystems;
  in {
    devShells = forAllSystems (
      system: let
        pkgs = import nixpkgs {inherit system;};

        javaFxLibs = with pkgs; [
          glib
          gtk3
          libGL
          libx11
          libxtst
          libxxf86vm
        ];
      in {
        default = pkgs.mkShell {
          packages = with pkgs; [
            jdk17
            maven
          ];
          shellHook =
            /*
            bash
            */
            ''
              echo "Welcome to the Java devShell on ${system}."
              java -version

              export LD_LIBRARY_PATH="${pkgs.lib.makeLibraryPath javaFxLibs}:$LD_LIBRARY_PATH"
            '';
        };
      }
    );
  };
}
