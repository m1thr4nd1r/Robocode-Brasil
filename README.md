# Robocode-Brasil

## Como rodar os robôs

1. Mova a pasta do projeto(`Robocode-Brasil`) para a pasta dos robôs do robocode (normalmente fica em `~/robocode/robots/`);

2. Renomei a pasta do projeto de `Robocode-Brasil` para `orion`, por causa dos packages das classes (no terminal: `mv Robocode-Brasil orion`);

3. Dentro da pasta do projeto, compile os arquivos `.java` com o comando:

```shell
    javac -cp  ~/robocode/libs/robocode.jar ./*.java && javac -cp ~/robocode/libs/robocode.jar ./*/*.java
 ```

4. Agora é só executar o robocode. Dentro da pasta do robocode, fora da pasta do projeto, execute: `./robocode.sh` ou `sh robocode.sh`

5. E que as batalhas começem!!!
