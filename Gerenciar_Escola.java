package aula_07;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JOptionPane;

/*
   Uma escola necessita que você implemente um software no qual seja possível 
cadastrar professores, alunos e turmas sem uma quantidade definida, ou seja, 
para qualquer número de pessoas ou turmas. Cada novo aluno cadastrado deve 
pertencer a apenas uma turma. As turmas serão identificadas apenas por um 
número (índice ou Id) que poderá ser reorganizado se qualquer outra turma 
for removida. A escola também necessita que você cadastre os professores 
em uma lista separada, porém, sem nenhum vínculo de turma, apenas 
apresentando nome e matrícula do professor. 

   Apesar de utilizar apenas o nome do aluno para vincular à turma e o nome 
do professor ao cadastro, a escola deseja que você obtenha a matrícula, 
o cpf, o rg e o endereço dos alunos e dos professores mesmo que esses 
dados não sejam gravados na sua totalidade.
*/

public class Gerenciar_Escola {
    
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<Professor> listaProfessores = new ArrayList<>();
    static LinkedHashMap<String,ArrayList<Aluno>> listaTurmas = new LinkedHashMap<>();
    
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, ">>> Bem-Vindo ao gerenciador escolar <<<");
        
        String escolhaMenu;
        while (true) {
            escolhaMenu = menu();
            
            if (!"1".equals(escolhaMenu) && !"2".equals(escolhaMenu) && !"3".equals(escolhaMenu) && 
                    !"4".equals(escolhaMenu) && !"5".equals(escolhaMenu) && !"6".equals(escolhaMenu)) {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            } else {
                switch (escolhaMenu) {
                    case "1" -> {
                        cadastro();
                    }
                    case "2" -> {
                        if (listaAlunos.isEmpty() && listaTurmas.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há nem alunos nem turmas cadastradas!");
                        } else if (listaAlunos.isEmpty() && !listaTurmas.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados!");
                        } else if (!listaAlunos.isEmpty() && listaTurmas.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há turmas cadastradas!");
                        } else {
                            matriculandoAluno();
                        }
                    }
                    case "3" -> {
                        verDados();
                    }
                    case "4" -> {
                        if (listaAlunos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados!");
                        } else {
                            int cont = 0;
                            for (int i = 0; i < listaAlunos.size(); i++) {
                                if (listaAlunos.get(i).alunoMatriculado == true) {
                                    cont ++;
                                }
                            }
                            if (cont >= 1) {
                                desmatriculandoAlunoDaTurma();
                            } else {
                                JOptionPane.showMessageDialog(null, "Não há alunos matriculados!");
                            }
                        }
                    }
                    case "5" -> {
                        cancelarCadastro();
                    }
                    case "6" -> {
                        JOptionPane.showMessageDialog(null, "Programa finalizado com sucesso!!!");
                        System.exit(0);
                    } 
                }
            }
        }
        
    }
    
    public static String menu() {
        return JOptionPane.showInputDialog("""
                                           >>> Escolha uma opção <<<
                                           1. Cadastro
                                           2. Matricular aluno em turma
                                           3. Ver dados
                                           4. Desmatricular aluno da turma
                                           5. Cancelar cadastro
                                           6. Sair
                                           """);
    }
    
    public static void cadastro() {
        String escolhaCadastro;
        while (true) {
            escolhaCadastro = JOptionPane.showInputDialog("""
                                                          O que deseja cadastrar [0 para voltar]?
                                                          1. Aluno
                                                          2. Professor
                                                          3. Turma
                                                          """);
            if (!"0".equals(escolhaCadastro) && !"1".equals(escolhaCadastro) && 
                    !"2".equals(escolhaCadastro) && !"3".equals(escolhaCadastro)) {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            } else {
                switch (escolhaCadastro) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> {
                        String nome = JOptionPane.showInputDialog(">>> Cadastrando aluno(a) <<<\nNome:");
                        String matricula = JOptionPane.showInputDialog(">>> Cadastrando aluno(a) <<<\nMatrícula:");
                        String cpf = JOptionPane.showInputDialog(">>> Cadastrando aluno(a) <<<\nCPF:");
                        String rg = JOptionPane.showInputDialog(">>> Cadastrando aluno(a) <<<\nRG:");
                        String endereco = JOptionPane.showInputDialog(">>> Cadastrando aluno(a) <<<\nEndereço:");
                        
                        Aluno aluno = new Aluno(nome, matricula, cpf, rg, endereco, false, null);
                        listaAlunos.add(aluno);
                        
                        JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
                    }
                    case "2" -> {
                        String nome = JOptionPane.showInputDialog(">>> Cadastrando professor(a) <<<\nNome:");
                        String matricula = JOptionPane.showInputDialog(">>> Cadastrando professor(a) <<<\nMatrícula:");
                        String endereco = JOptionPane.showInputDialog(">>> Cadastrando professor(a) <<<\nEndereço:");
                        
                        Professor professor = new Professor(nome, matricula, endereco);
                        listaProfessores.add(professor);
                        
                        JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!");
                    }
                    case "3" -> {
                         String indiceTurma = JOptionPane.showInputDialog(">>> Cadastrando turma <<<\nÍndice da turma: ");
                         
                         listaTurmas.put(indiceTurma, new ArrayList<>());
                         
                         JOptionPane.showMessageDialog(null, "Turma cadastrada com sucesso!");
                    }
                }
                
            }
        }
    }
    
    public static void matriculandoAluno() {
        StringBuilder mensagem1 = new StringBuilder("Qual aluno deseja matricular [0 para voltar]?\n");
        for (int i = 0; i < listaAlunos.size(); i++) {
            mensagem1.append(i + 1).append(". ").append(listaAlunos.get(i).nome);
            if (listaAlunos.get(i).alunoMatriculado) {
                mensagem1.append(" - Matriculado\n");
            } else {
                mensagem1.append(" - Não matriculado\n");
            }
        }
        int alunoEscolhido;
        while (true) {
            alunoEscolhido = Integer.parseInt(JOptionPane.showInputDialog(mensagem1));
            
            if (alunoEscolhido == 0) {
                return;
            } else if (alunoEscolhido > 0 && alunoEscolhido <= listaAlunos.size() &&
                    listaAlunos.get(alunoEscolhido - 1).alunoMatriculado == false) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }
        
        ArrayList<String> chavesTurma = new ArrayList<>(listaTurmas.keySet());
        StringBuilder mensagem2 = new StringBuilder("Em que turma deseja matriculá-lo(a) [0 para voltar]?\n");
        for (int i = 0; i < chavesTurma.size(); i++) {
            mensagem2.append(i + 1).append(". Turma: ").append(chavesTurma.get(i)).append("\n");
        }
        int turmaEscolhida;
        while (true) {
            turmaEscolhida = Integer.parseInt(JOptionPane.showInputDialog(mensagem2));
            if (turmaEscolhida == 0) {
                return;
            } else if (turmaEscolhida > 0 && turmaEscolhida <= listaTurmas.size()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }

        listaTurmas.get(chavesTurma.get(turmaEscolhida - 1)).add(listaAlunos.get(alunoEscolhido - 1));
        listaAlunos.get(alunoEscolhido - 1).alunoMatriculado = true;
        listaAlunos.get(alunoEscolhido - 1).turmaMatriculada = chavesTurma.get(turmaEscolhida - 1);

        JOptionPane.showMessageDialog(null,
                "Matrícula bem sucedida!\nAluno " + listaAlunos.get(alunoEscolhido - 1).nome + 
                        " foi cadastrado na Turma " + chavesTurma.get(turmaEscolhida - 1));
        
    }
    
    public static void verDados() {
        String x;
        while(true) {
            x = JOptionPane.showInputDialog("""
                                            O que deseja ver [0 para voltar]?
                                            1. Alunos
                                            2. Professores
                                            3. Turmas
                                            """);
            if (!"0".equals(x) && !"1".equals(x) && !"2".equals(x) && !"3".equals(x)) {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            } else {
                switch (x) {
                    case "1" -> {
                        verDadosAluno();
                    }
                    case "2" -> {
                        verDadosProfessor();
                    }
                    case "3" -> {
                        verDadosTurma();
                    }
                    case "0" -> {
                        return;
                    }

                }
            }
        } 
    }
    
    public static void verDadosAluno() {
        if (listaAlunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados!");
        } else {
            StringBuilder mensagem1 = new StringBuilder("Escolha o aluno [0 para voltar]:\n");
            for (int i = 0; i < listaAlunos.size(); i++) {
                mensagem1.append(i + 1).append(". ").append(listaAlunos.get(i).nome).append("\n");
            }

            int escolha;
            while (true) {
                escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem1));
                if (escolha == 0) {
                    return;
                } else if (escolha > 0 && escolha <= listaAlunos.size()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
                }
            }

            StringBuilder mensagem2 = new StringBuilder("Dados do aluno:\n");
            mensagem2.append("Nome: ").append(listaAlunos.get(escolha - 1).nome).append("\n");
            mensagem2.append("Matrícula: ").append(listaAlunos.get(escolha - 1).matricula).append("\n");
            mensagem2.append("CPF: ").append(listaAlunos.get(escolha - 1).cpf).append("\n");
            mensagem2.append("RG: ").append(listaAlunos.get(escolha - 1).rg).append("\n");
            mensagem2.append("Endereço: ").append(listaAlunos.get(escolha - 1).endereco).append("\n");
            if (listaAlunos.get(escolha - 1).alunoMatriculado == true) {
                mensagem2.append("Aluno matriculado na turma: ").append(listaAlunos.get(escolha - 1).turmaMatriculada).append("\n");
            } else {
                mensagem2.append("Aluno ainda não foi matriculado\n");
            }
            JOptionPane.showMessageDialog(null, mensagem2);
            
        }
    }
    
    public static void verDadosProfessor() {
        if (listaProfessores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há professores cadastrados!");
        } else {
            StringBuilder mensagem1 = new StringBuilder("Escolha o professor [0 para voltar]:\n");
            for (int i = 0; i < listaProfessores.size(); i++) {
                mensagem1.append(i + 1).append(". ").append(listaProfessores.get(i).nome).append("\n");
            }

            int escolha;
            while (true) {
                escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem1));
                if (escolha == 0) {
                    return;
                } else if (escolha > 0 && escolha <= listaProfessores.size()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
                }
            }

            StringBuilder mensagem2 = new StringBuilder("Dados do professor:\n");
            mensagem2.append("Nome: ").append(listaProfessores.get(escolha - 1).nome).append("\n");
            mensagem2.append("Matrícula: ").append(listaProfessores.get(escolha - 1).matricula).append("\n");
            mensagem2.append("Endereço: ").append(listaProfessores.get(escolha - 1).endereco).append("\n");
            JOptionPane.showMessageDialog(null, mensagem2);
            
        }
    }
    
    public static void verDadosTurma() {
        if (listaTurmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há turmas cadastradas!");
        } else {
            ArrayList<String> chavesTurma = new ArrayList(listaTurmas.keySet());
            StringBuilder mensagem = new StringBuilder("Turmas cadastradas:\n");
            for (int i = 0; i < chavesTurma.size(); i++) {
                mensagem.append("\nTurma ").append(chavesTurma.get(i)).append(": ");
                ArrayList<Aluno> alunos = listaTurmas.get(chavesTurma.get(i));

                if (alunos.isEmpty()) {
                    mensagem.append(" (Sem alunos)");
                } else {
                    for (Aluno a : alunos) {
                        mensagem.append(a.nome).append(" ");
                    }
                }
            }
            
            JOptionPane.showMessageDialog(null, mensagem);
            
        }
    }
    
    public static void desmatriculandoAlunoDaTurma() {
        StringBuilder mensagem = new StringBuilder("Qual aluno deseja desmatricular [0 para voltar]?\n");
        for (int i = 0; i < listaAlunos.size(); i++) {
            mensagem.append(i + 1).append(". ").append(listaAlunos.get(i).nome).append("\n");
        }
        
        int escolha;
        while (true) {
            escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem));
            if (escolha == 0) {
                return;
            } else if (escolha > 0 && escolha <= listaAlunos.size() && listaAlunos.get(escolha - 1).alunoMatriculado == true) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }
        
        Aluno aluno = listaAlunos.get(escolha - 1);
        String turma = aluno.turmaMatriculada;

        if (turma != null && listaTurmas.containsKey(turma)) {
            listaTurmas.get(turma).remove(aluno);
        }
        
        aluno.alunoMatriculado = false;
        aluno.turmaMatriculada = null;
        JOptionPane.showMessageDialog(null, "Aluno desmatriculado com sucesso!");
    }
    
    public static void cancelarCadastro() {
        String x;
        while(true) {
            x = JOptionPane.showInputDialog("""
                                            O que deseja ver [0 para voltar]?
                                            1. Alunos
                                            2. Professores
                                            3. Turmas
                                            """);
            if (!"0".equals(x) && !"1".equals(x) && !"2".equals(x) && !"3".equals(x)) {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            } else {
                switch (x) {
                    case "1" -> {
                        if (listaAlunos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados!");
                        } else {
                            cancelarCadastroAluno();
                        }
                    }
                    case "2" -> {
                        if (listaProfessores.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há professores cadastrados!");
                        } else {
                            cancelarCadastroProfessor();
                        }
                    }
                    case "3" -> {
                        if (listaTurmas.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Não há turmas cadastradas!");
                        } else {
                            cancelarTurma();
                        }
                    }
                    case "0" -> {
                        return;
                    }
                }
            }      
        }
    }
    
    public static void cancelarCadastroAluno() {
        StringBuilder mensagem = new StringBuilder("Qual aluno deseja cancelar o cadastro [0 para voltar]?\n");
        for (int i = 0; i < listaAlunos.size(); i++) {
            mensagem.append(i + 1).append(". ").append(listaAlunos.get(i).nome).append("\n");
        }
        int escolha;
        while (true) {
            escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem));
            
            if (escolha == 0) {
                return;
            } else if (escolha > 0 && escolha <= listaAlunos.size()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }
        Aluno alunoRemovido = listaAlunos.get(escolha - 1);
        
        if (alunoRemovido.alunoMatriculado && alunoRemovido.turmaMatriculada != null) {
            if (listaTurmas.containsKey(alunoRemovido.turmaMatriculada)) {
                listaTurmas.get(alunoRemovido.turmaMatriculada).remove(alunoRemovido);
            }
        }

        listaAlunos.remove(alunoRemovido);
        JOptionPane.showMessageDialog(null, "Cadastro do aluno cancelado com sucesso!");
    }
    
    public static void cancelarCadastroProfessor() {
        StringBuilder mensagem = new StringBuilder("Qual professor deseja cancelar o cadastro [0 para voltar]?\n");
        for (int i = 0; i < listaProfessores.size(); i++) {
            mensagem.append(i + 1).append(". ").append(listaProfessores.get(i).nome).append("\n");
        }
        int escolha;
        while (true) {
            escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem));
            
            if (escolha == 0) {
                return;
            } else if (escolha > 0 && escolha <= listaProfessores.size()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }
        
        listaProfessores.remove(listaProfessores.get(escolha - 1));
        JOptionPane.showMessageDialog(null, "Cadastro do professor cancelado com sucesso!");
        
    }
    
    public static void cancelarTurma() {
        ArrayList<String> chavesTurmas = new ArrayList<>(listaTurmas.keySet());
        StringBuilder mensagem = new StringBuilder("Qual turma deseja cancelar [0 para voltar]?\n");
        for (int i = 0; i < chavesTurmas.size(); i++) {
            mensagem.append(i + 1).append(". ").append(chavesTurmas.get(i)).append("\n");
        }
        int escolha;
        while (true) {
            escolha = Integer.parseInt(JOptionPane.showInputDialog(mensagem.toString()));
            
            if (escolha == 0) {
                return;
            } else if (escolha > 0 && escolha <= chavesTurmas.size()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "ERRO! Opção inválida.");
            }
        }
        
        String chaveSelecionada = chavesTurmas.get(escolha - 1);
        ArrayList<Aluno> alunosDaTurma = listaTurmas.get(chaveSelecionada);
        
        if (alunosDaTurma.isEmpty()) {
            listaTurmas.remove(chaveSelecionada);
            JOptionPane.showMessageDialog(null, "Turma removida com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível remover uma turma com alunos matriculados!");
        }
    }
}

class Aluno {
    String nome;
    String matricula;
    String cpf;
    String rg;
    String endereco;
    boolean alunoMatriculado;
    String turmaMatriculada;
    public Aluno(String nome, String matricula, String cpf, String rg, String endereco, boolean alunoMatriculado, String turmaMatriculada) {
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.alunoMatriculado = alunoMatriculado;
        this.turmaMatriculada = turmaMatriculada;
    }
}

class Professor {
    String nome;
    String matricula;
    String endereco;
    public Professor(String nome, String matricula, String endereco) {
        this.nome = nome;
        this.matricula = matricula;
        this.endereco = endereco;
    }
}
