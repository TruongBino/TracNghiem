package com.example.tracnghiem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.txtQuestion.setText(question.getQuestion());
        holder.txtOption1.setText(question.getOptions().get(0));
        holder.txtOption2.setText(question.getOptions().get(1));
        holder.txtOption3.setText(question.getOptions().get(2));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView txtQuestion, txtOption1, txtOption2, txtOption3;

        public QuestionViewHolder(View view) {
            super(view);
            txtQuestion = view.findViewById(R.id.txt_question);
            txtOption1 = view.findViewById(R.id.txt_option1);
            txtOption2 = view.findViewById(R.id.txt_option2);
            txtOption3 = view.findViewById(R.id.txt_option3);
        }
    }
}
