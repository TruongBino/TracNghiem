package com.example.tracnghiem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        // Lấy thông tin kết quả từ Intent
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        TextView textViewCorrectAnswers = findViewById(R.id.textViewCorrectAnswers);
        textViewCorrectAnswers.setText("Số câu trả lời đúng: " + correctAnswers + "/" + totalQuestions);

        TextView textViewPercentage = findViewById(R.id.textViewPercentage);
        textViewPercentage.setText("Phần trăm đúng: " + String.format("%.2f", percentage) + "%");

        TextView textViewResultMessage = findViewById(R.id.textViewResultMessage);
        if (percentage >= 80) {
            textViewResultMessage.setText("Chúc mừng! Bạn đã đậu bài trắc nghiệm!");
        } else {
            textViewResultMessage.setText("Rất tiếc! Bạn đã rớt bài trắc nghiệm!");
        }

        Button buttonOK = findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(view -> {
            // Xử lý khi người chơi ấn nút "OK"
            // Ví dụ: Chuyển đến màn hình chính hoặc màn hình kết quả
            finish();  // Đóng màn hình kết quả
        });
    }
}