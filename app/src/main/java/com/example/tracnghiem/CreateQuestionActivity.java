package com.example.tracnghiem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionActivity extends AppCompatActivity {
        private EditText editQuestion, editOption1, editOption2, editOption3;
        private Button btnSave;
        private RecyclerView recyclerView;
        private DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_question);

            editQuestion = findViewById(R.id.edit_question);
            editOption1 = findViewById(R.id.edit_option1);
            editOption2 = findViewById(R.id.edit_option2);
            editOption3 = findViewById(R.id.edit_option3);
            btnSave = findViewById(R.id.btn_save);
            recyclerView = findViewById(R.id.recycler_view);

            databaseReference = FirebaseDatabase.getInstance().getReference("questions");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveQuestion();
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            List<Question> questionList = new ArrayList<>();
            QuestionAdapter adapter = new QuestionAdapter(questionList);
            recyclerView.setAdapter(adapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    questionList.clear();
                    for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                        Question question = questionSnapshot.getValue(Question.class);
                        questionList.add(question);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }

        private void saveQuestion() {
            String question = editQuestion.getText().toString().trim();
            String option1 = editOption1.getText().toString().trim();
            String option2 = editOption2.getText().toString().trim();
            String option3 = editOption3.getText().toString().trim();

            if (TextUtils.isEmpty(question)) {
                Toast.makeText(this, "Vui lòng nhập câu hỏi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2) || TextUtils.isEmpty(option3)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ các phương án trả lời", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> options = new ArrayList<>();
            options.add(option1);
            options.add(option2);
            options.add(option3);

            int answerIndex = 0; // Giả sử đáp án đúng là phương án 1
            Question newQuestion = new Question(question, options, answerIndex);
            databaseReference.push().setValue(newQuestion);

            editQuestion.setText("");
            editOption1.setText("");
            editOption2.setText("");
            editOption3.setText("");
        }
}
