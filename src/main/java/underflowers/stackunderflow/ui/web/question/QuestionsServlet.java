package underflowers.stackunderflow.ui.web.question;

import underflowers.stackunderflow.domain.question.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuestionsServlet", urlPatterns = "/questions")
public class QuestionsServlet extends javax.servlet.http.HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(Question.builder()
                .author("Gollgot")
                .title("How to resolve issue with Python 3 modules not building in cross-compile ?")
                .content("I want to cross-compile python 3.7 for my SoC-FPGA ARM board since it's OS is old (Angstrom 2014) it does not have binaries and libs to compile the python on it so the only option is to cross-compile ...")
            .build()
        );

        questions.add(Question.builder()
                .author("Sikiewz")
                .title("test.exe is not valid win32 application after link with ld in windows")
                .content("Hi there I want to ask a question how can I fix 'test.exe' is not a valid Win32 application after I link the test.o. Currently I use windows 7. please help me to fix this problem")
                .build()
        );

        request.setAttribute("questions", questions);
        request.getRequestDispatcher("/WEB-INF/views/questions.jsp").forward(request, response);
    }

}
