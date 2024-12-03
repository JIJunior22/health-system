package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UsuarioDao {
    private Usuario logado;
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEmc() {
        return em;
    }

    public void criar(Usuario usuario) {

        try {
            getEmc().getEntityManager().getTransaction().begin();
            getEmc().getEntityManager().persist(usuario);
            getEmc().getEntityManager().getTransaction().commit();


            System.out.println(String.format("""
                            ╔══════════════════════════════════════╗
                            ║         Usuário Cadastrado           ║
                            ╚══════════════════════════════════════╝
                            Código         : %d
                            Nome           : %s
                                                        
                                                        
                                                        
                            Data Nascimento: %s
                            Sexo           : %s
                            Peso           : %.2f kg
                            Altura         : %.2f m
                            ═══════════════════════════════════════
                            """,
                    usuario.getCod(),
                    usuario.getNome(),

                    usuario.getDataNascimento(),
                    usuario.getSexo(),
                    usuario.getPeso(),
                    usuario.getAltura()));

        } finally {

        }
    }


    public Usuario buscarPorNome(String nome) {
        getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager().createNamedQuery("usuarios.getByName");
        query.setParameter("nome", nome);
        return (Usuario) query.getSingleResult();

    }


    public List<Usuario> findAll() {
        //  getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager().createNamedQuery("usuarios.listar");

        return query.getResultList();
    }

    public void exibirTodosUsuarios() {
        List<Usuario> usuarios = findAll();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.println("Lista de usuários encontrados:");
            for (Usuario usuario : usuarios) {
                System.out.println(String.format("""
                                ╔═══════════════════════════════╗
                                ║        Dados do Usuário       ║
                                ╠═══════════════════════════════╣
                                ║ Código: %-5d                  ║
                                ║ Nome  : %-50s                 ║
                                ║                               ║
                                ║                               ║
                                ║ Peso  : %-10.2f kg            ║
                                ║ Altura: %-10.2f m             ║
                                ╚═══════════════════════════════╝
                                """,
                        usuario.getCod(),
                        usuario.getNome(),

                        usuario.getPeso(),
                        usuario.getAltura()));
            }
        }
    }

    public Usuario exibirUsuarioPorId(int id) {
        Usuario usuario = findById(id);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
        } else {
            System.out.println(String.format("""
                            ╔══════════════════════════════════════╗
                            ║          Detalhes do Usuário         ║
                            ╠══════════════════════════════════════╣
                            ║ COD          : %d                    ║ 
                            ║ Nome         : %s                    ║
                            ║                                      ║
                            ║ Peso         : %.2f kg               ║
                            ║ Altura       : %.2f m                ║
                            ╚══════════════════════════════════════╝
                            """,
                    usuario.getCod(), usuario.getNome(),
                    usuario.getPeso(), usuario.getAltura()));
        }
        return usuario;
    }


    public Usuario findById(int id) {
        getEmc().getEntityManager().getTransaction().begin();
        return getEmc().getEntityManager().find(Usuario.class, id);
    }

    public void deleteById(int id) {
        var codigoUsuario = findById(id);

        //getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().remove(codigoUsuario);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
        System.out.println("Usuario: " + codigoUsuario.getNome() + " removido do banco de dados");
    }


    public void update(Usuario usuarioAtualizado, int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("healthsystem");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Usuario usuarioExistente = em.find(Usuario.class, id);

            if (usuarioExistente == null) {
                System.out.println("Usuário com ID " + id + " não encontrado.");
                return;
            }


            if (usuarioAtualizado.getNome() != null) {
                usuarioExistente.setNome(usuarioAtualizado.getNome());
            }

            if (usuarioAtualizado.getDataNascimento() != null) {
                usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
            }
            if (usuarioAtualizado.getSexo() != null) {
                usuarioExistente.setSexo(usuarioAtualizado.getSexo());
            }
            if (usuarioAtualizado.getPeso() != 0) {
                usuarioExistente.setPeso(usuarioAtualizado.getPeso());
            }
            if (usuarioAtualizado.getAltura() != 0) {
                usuarioExistente.setAltura(usuarioAtualizado.getAltura());
            }


            em.merge(usuarioExistente);
            em.getTransaction().commit();

            System.out.println("Dados atualizados para o usuário: " + usuarioExistente.getNome());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
            emf.close();
        }

    }


}



