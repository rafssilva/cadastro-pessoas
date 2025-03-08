
*** O QUE VOCÊ VAI PRECISAR ***

Versão Java
8 (8u441)
https://www.oracle.com/webapps/redirect/signon?nexturl=https://download.oracle.com/otn/java/jdk/8u441-b07/7ed26d28139143f38c58992680c214a5/jdk-8u441-windows-x64.zip
(Esteja logado na conta Oracle antes de abrir o link)


Versão Eclipse
2019-09-R
https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2019-09/R/eclipse-jee-2019-09-R-win32-x86_64.zip
(Versões mais recentes vêm com Java / Jakarta já embutido e pode causar problemas com o Java 8 que vamos utilizar)


Versão WildFly
18.0.0.Final
https://download.jboss.org/wildfly/18.0.0.Final/wildfly-18.0.0.Final.zip


Versão PostgreSQL
MAIS RECENTE
https://sbp.enterprisedb.com/getfile.jsp?fileid=1259402



***********************************************************************



a pasta setup possui dois outros arquivos dentro (kumulus_db_backup.zip e postgreSQL.zip) alem deste README



*** COMEÇANDO PELO BANCO ***

extraia o arquivo kumulus_db_backup.zip em um diretório de sua preferência

instalar o postgresql

abrir a interface pgadmin

criar um banco com o mesmo nome do banco o qual foi gerado o backup
(nome do banco: kumulus_db)

clicar com o direito sobre o banco (kumulus_db) dentro da interface pgadmin
selecionar a opção restore

em format selecionar custom ou tar

em filename buscar o arquivo de backup que foi extraído de kumulus_db_backup.zip

clicar em restore

clicar com o direito sobre o banco após o backup
selecionar a opção refresh



*** O PROJETO ***

abra a ide eclipse

no menu superior, clique em file > import...

na tela que vai abrir busque por maven e selecione a opção existing maven projects

clique em next

em root directory clique em browse

navegue ate o diretório onde está a pasta crud (cadastro-pessoas-master -> crud) e clique em selecionar pasta

clique em finish

no menu superior, clique em help > eclipse marketplace

busque pelo plugin jboss tools e instale-o

baixe o wildfly versão wildfly-18.0.0.Final (.zip)

extraia o wildfly para um diretório de sua preferência

na ide eclipse, na perspectiva java ee, procure a aba servers no painel inferior

clique com o direito no painel e selecione new > server

na tela que vai abrir busque por wildfly, selecione wildfly 18 e clique em next

clique em next novamente

em home directory clique em browse
procure e selecione a pasta do wildfly no diretorio onde vc o extraiu

em runtime jre, clique em installed jres...
clique em add... selecione standard vm e clique em next

em jre home clique em directory... e selecione a pasta da jdk (jdk1.8.0_441)
clique em selecionar pasta e depois em finish

certifique-se da jdk selecionada estar com a caixa marcada e clique em apply and close

depois voltando pra runtime jre
seleciona alternate jre e selecione a jdk (jdk1.8.0_441)
clique em finish

clique com o direito sobre o server wildfly 18 no painel inferior
selecione add and remove...
clique em add all e depois em finish

extraia o arquivo postgreSQL.zip para um diretório de sua preferencia e copie a pasta extraida

vá para a pasta raiz do wildfly (wildfly-18.0.0.Final)
navegue até [pasta raiz onde wildfly foi extraído]wildfly-18.0.0.Final\modules\system\layers\base\org
cole a pasta postgreSQL extraída de postgreSQL.zip

na ide eclipse
procure pelo wildfly na aba servers no painel inferior
clique na seta a esquerda para expandir o painel de configurações
expanda filesets > configuration file e abra o arquivo standalone.xml

na seçao <datasources> cole o datasource abaixo

<datasource jndi-name="java:/jdbc/crudDS" pool-name="crudDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
                    <connection-url>jdbc:postgresql://localhost:5432/kumulus_db?autoReconnect=true</connection-url>
                    <driver>postgreSQL</driver>
                    <security>
                        <user-name>postgres</user-name>
                        <password>admin</password>
                    </security>
                </datasource>

certifique-se de alterar o user-name, o password e a porta (5432) de acordo com as suas configurações no postgresql

logo abaixo na seção <drivers> cole o driver abaixo

<driver name="postgreSQL" module="org.postgreSQL">
                        <driver-class>org.postgresql.Driver</driver-class>
                    </driver>

inicie o servidor e acesse a aplicação no navegador por http://localhost:8080/crud



*** OBSERVAÇÕES ***

foi criada uma tela para cadastro de tipos de endereço
para que o usuário possa identificar cada endereço de pessoa cadastrada,
tendo a flexibilidade de escolher quais tipos ele deseja ter disponivel para cadastrar nos endereços

aplicação está distribuída em bean service e repository
onde o bean representa as operaçoes em tela,
service aplica as regras de negocio quando houver e
repository é a camada de dados

ao acessar a tela de tipos de endereço, há um campo superior que serve como
filtro para buscar tipos de endereços ja cadastrados e realizar operaçoes

ao acessar a tela gestão de pessoas, há campos na parte superior que servem
como filtros para buscar pessoas e podem ser utilizados de forma individual ou combinados

para cada pessoa, é possivel realizar operaçoes como atualizar ou excluir
e cadastrar endereços para a mesma

não há tela para cadastrar municipios e estados,
mas o banco ja possui alguns municipios e estados cadastrados
para simular dados vindos importados de bases confiaveis como correios etc.
