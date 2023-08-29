package lk.test.myapplication.models.task;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {
    @GET("tasks")
    Call<TaskResponse> tasks();
}
