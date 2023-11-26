package com.example.tracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.nextButton);

        // Truy xuất dữ liệu từ Firebase Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("questions");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List questions = new ArrayList<>();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    String questionText = questionSnapshot.child("questionText").getValue(String.class);
                    List options = new ArrayList<>();
                    for (DataSnapshot optionSnapshot : questionSnapshot.child("options").getChildren()) {
                        String optionText = optionSnapshot.getValue(String.class);
                        options.add(optionText);
                    }
                    Integer correctOptionIndexInteger = questionSnapshot.child("correctOptionIndex").getValue(Integer.class);
                    int correctOptionIndex;
                    if (correctOptionIndexInteger != null) {
                        correctOptionIndex = correctOptionIndexInteger;
                    } else {
                        correctOptionIndex = 0; // Gán một giá trị mặc định nếu không có giá trị
                    }
                    // Tạo đối tượng Question và thêm vào danh sách
                    questions.add(new Question(questionText, options, correctOptionIndex));
                }
                // Khởi tạo trò chơi trắc nghiệm
                quiz = new Quiz(questions);
                // Hiển thị câu hỏi đầu tiên
                displayCurrentQuestion();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy xuất dữ liệu từ Firebase Realtime Database
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra câu trả lời của người chơi
                int selectedOptionIndex = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedOptionIndex != -1) {
                    boolean isCorrect = quiz.checkAnswer(optionsRadioGroup.indexOfChild(findViewById(selectedOptionIndex)));
                    if (isCorrect) {
                        Toast.makeText(QuizActivity.this, "Đúng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(QuizActivity.this, "Sai", Toast.LENGTH_SHORT).show();
                    }
                    // Chuyển đến câu hỏi tiếp theo
                    if (!quiz.isLastQuestion()) {
                        quiz.moveToNextQuestion();
                        displayCurrentQuestion();
                    } else {
                        displayEndQuizMessage(isCorrect);
                        Toast.makeText(QuizActivity.this, "Bài trắc nghiệm kết thúc", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(QuizActivity.this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayCurrentQuestion() {
        Question currentQuestion = quiz.getCurrentQuestion();
        questionTextView.setText(currentQuestion.getQuestionText());
        optionsRadioGroup.removeAllViews();
        for (String option : currentQuestion.getOptions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsRadioGroup.addView(radioButton);
        }
    }
    private void displayEndQuizMessage(boolean isCorrect) {

        if (isCorrect) {
            // Hiển thị thông báo câu hỏi cuối cùng đúng
            Toast.makeText(QuizActivity.this, "Đúng", Toast.LENGTH_SHORT).show();
        } else {
            // Hiển thị thông báo câu hỏi cuối cùng sai
            Toast.makeText(QuizActivity.this, "Sai", Toast.LENGTH_SHORT).show();
        }
    }

}