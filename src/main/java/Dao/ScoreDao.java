package Dao;

import model.Score;

import java.util.List;

public interface ScoreDao {
    boolean addScore(Score score);
    boolean deleteScore(String scoreId);
    boolean updateScore(String scoreId, Integer newScore);
    List<Score> getAllScores();
    List<Score> getAllScoresByPage(int page, int pageSize);
    int getTotalScoreCount();
    Score getScoreById(String scoreId);
    List<Score> getScoresByStudentId(String stuId);
    List<Score> getScoresByStudentIdByPage(String stuId, int page, int pageSize);
    int getScoreCountByStudentId(String stuId);
    List<Score> searchScores(String keyword, String semester, String academicYear, int page, int pageSize);
    int getSearchScoreCount(String keyword, String semester, String academicYear);
}
