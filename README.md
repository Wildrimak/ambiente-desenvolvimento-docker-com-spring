# Ambiente Spring para docker 

Essa aplicação executa um hot reload na aplicação gerada pelo docker atualizando as informações do pacote final já no jar de produção.

### Instruções

*  Configure o pom para ter o devtools no momento de build da aplicação também:


```
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludeDevtools>false</excludeDevtools>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

*  Coloque essa configuração no application.properties:

 	```spring.devtools.remote.secret=123 //esse número é uma senha qualquer```

* Em ```Run Configurations -> Java Application -> New Configuration``` faça:

  * Em Project deixe seu projeto atual
  * Em Main Class coloque: ```org.springframework.boot.devtools.RemoteSpringApplication```
  * Na aba arguments coloque ```http://localhost:[your-port]```
  * Der um nome para a configuração, no meu caso coloquei Remote e clique em close
  * Adicione essa configuração na aba Common para ela aparecer no seu menu
  * Após esses passos ele vai tentar executar sua aplicação no endereço passado, com aquela porta ativa que no caso será configurada com o docker

* No terminal dentro do seu projeto maven gere um jar da sua aplicação que será atualizado de tempos em tempos no docker.
  * ```./mvnw clean package -DskipTests```
* Crie um dockefile como o abaixo:

	```yml
	FROM openjdk
	WORKDIR /app
	COPY target/[your-project]-SNAPSHOT.jar /app/[your-project].jar
	ENTRYPOINT ["java", "-jar", "[your-project].jar"] 
	```
* Gere a imagem com ```docker build -t [name-for-your-image] . ``` 
* Execute o ambiente com: ```docker run -p [your-port]:8080 [name-for-your-image]```
*  Vá em ```Run Configurations -> Java Application -> [Your-configuration-name]``` e clique em run
* Tente modificar algo na sua aplicação que ative o hot reload e veja no navegador ou console o resultado

### Notas

* O seu jar é jogado dentro do LXC do docker do java mas em nenhum momento recupero essa jar novamente, em outras palavras se eu fechar o container e abrir de novo ele será exatamente igual a como eu gerei o jar pela primeira vez.

* Para contornar isso pare os containers gere novamente seu jar com ```./mvnw clean package -DskipTests``` assim terás a versão mais atualizada do seu jar na sua maquina e se quiser continuar a partir dai no container faça novamente:
  * Gere a imagem com ```docker build -t [name-for-your-image] . ``` 
  * Execute o ambiente com: ```docker run -p [your-port]:8080 [name-for-your-image]```