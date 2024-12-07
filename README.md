
  ![image](https://github.com/user-attachments/assets/6a800045-a753-40c1-bd55-251354e13e1e)


# Health System

Bem-vindo ao **Health System**! Este sistema foi desenvolvido para ajudar os usuários a monitorar sua saúde de forma eficiente, registrando indicadores como frequência cardíaca, pressão arterial e glicose, gerando relatórios personalizados e notificações para valores críticos.

---

## 📚 Contexto Acadêmico

Este projeto foi desenvolvido como requisito avaliativo durante o período letivo de 2024.2, referente à disciplina de Engenharia de Software II, no quarto período do curso de Ciência da Computação da Faculdade Católica da Paraíba.
## Visão Geral

### **Imagens do Sistema**  
![image](https://github.com/user-attachments/assets/567b3377-cdc5-4a0b-ac82-e80fcec33a02)
![image](https://github.com/user-attachments/assets/189b5fde-c8c3-4278-972d-cda568bf3ffc)
![image](https://github.com/user-attachments/assets/b928ec8a-9195-4067-bebb-c864f6ea237d)
![image](https://github.com/user-attachments/assets/e27169c8-9ed3-4a0e-985d-4468c9eb98a0)
![image](https://github.com/user-attachments/assets/354b9070-102d-4651-8f0f-f4afd48a3105)
![image](https://github.com/user-attachments/assets/b5763679-4744-451a-a557-75ed0f17e733)






---

## Funcionalidades

O **Health System** oferece as seguintes funcionalidades:

1. **Gestão de Usuários**: Cadastro, login e gerenciamento de perfis.
2. **Monitoramento de Saúde**: Registro de dados como frequência cardíaca, pressão arterial e glicose.
3. **Notificações Automáticas**: Alertas baseados em métricas de saúde críticas.
4. **Relatórios Personalizados**: Geração de relatórios flexíveis integrados à API Gemini.
5. **Interface Gráfica Moderna**: Navegação intuitiva por telas criadas com JavaFX.

---

## 🛠️ Tecnologias Utilizadas

O sistema foi desenvolvido utilizando as seguintes tecnologias e ferramentas:

- **Java 21**: Linguagem principal.
- **JavaFX**: Para a interface gráfica do usuário.
- **PostgreSQL**: Banco de dados relacional.
- **Maven**: Gerenciamento de dependências e construção do projeto.
- **API Gemini**: Para geração de relatórios personalizados.
- **Arquitetura Cliente-Servidor**: Para escalabilidade e organização.

Além disso, utiliza padrões de projeto como:

- **Singleton**: Gerenciamento de instâncias únicas de componentes principais.
- **Facade**: Centralização de operações complexas.
- **Builder**: Personalização de relatórios.

---

## 💻 Desenvolvedores
- <a href="https://github.com/lukaspersy">Lucas Pereira</a>
- <a href="https://github.com/victorferreira21">Victor Ferreira</a>


## 🚀 Configuração do Projeto

Para configurar e executar o projeto em sua máquina local, siga estas etapas:

1. Clone este repositório para o seu ambiente local:
   ```bash
   git clone https://github.com/seu-usuario/health-system.git


   

2. Importe o projeto em sua IDE Java preferida.
3. Configure o banco de dados PostgreSQL:
- Ajuste as credenciais no arquivo persistence.xml.
- Execute o script de inicialização do banco, se necessário.
4. Compile o projeto utilizando o Maven:
- mvn clean install
5. Execute o projeto a partir da classe principal (MainApplication.java) ou usando o comando:
- mvn javafx:run
