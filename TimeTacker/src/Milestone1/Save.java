package Milestone1;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Save class is designed to save a JSON file with information that
 * we will load when application restarts.
 * It will save all atributes of Activities and Intervals.
 */
public class Save implements Visitor {

  private FileWriter textEditor;
  private JSONObject jsonTree;
  private Project root;

  /**
   * Save Constructor.
   *
   * @param fileName - String of the name of the JSON.
   * @param root - Project object of the root project.
   */
  public Save(String fileName, Project root) {
    try {
      this.textEditor = new FileWriter(fileName);
      this.root = root;
      this.root.acceptVisitor(this);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // We want to accept ROOT to sabe the whole tree.
  @Override
  public void visitTask(Task task) {
    JSONObject json = new JSONObject();

    json.put("type", "task");
    json.put("name", task.getName());
    json.put("totalTime", task.getTotalTime());
    if (task.getInitialTime() != null) {
      json.put("initialTime", task.getInitialTime());
    } else {
      json.put("initialTime", JSONObject.NULL);
    }

    if (task.getFinalTime() != null) {
      json.put("finalTime", task.getFinalTime());
    } else {
      json.put("finalTime", JSONObject.NULL);
    }
    json.put("active", task.getActive());
    JSONArray intervals = new JSONArray();

    for (Interval interval : task.getIntervals()) {
      if (interval != null) {
        interval.acceptVisitor(this);
        intervals.put(this.jsonTree);
      }
    }
    json.put("intervals", intervals);
    this.jsonTree = json;
  }

  @Override
  public void visitProject(Project project) {
    JSONObject json = new JSONObject();

    json.put("type", "project");
    json.put("name", project.getName());
    json.put("totalTime", project.getTotalTime());
    if (project.getInitialTime() != null) {
      json.put("initialTime", project.getInitialTime());
    } else {
      json.put("initialTime", JSONObject.NULL);
    }

    if (project.getFinalTime() != null) {
      json.put("finalTime", project.getFinalTime());
    } else {
      json.put("finalTime", JSONObject.NULL);
    }

    JSONArray activities = new JSONArray();

    for (Activity activity : project.getActivities()) {
      if (activity != null) {
        activity.acceptVisitor(this);
        activities.put(this.jsonTree);
      }
    }
    json.put("activities", activities);
    this.jsonTree = json;

    // Si estem a root, ja podem guardar el JSON
    if (project.getFather() == null) {
      this.writeDocument();
    }

  }

  @Override
  public void visitInterval(Interval interval) {
    JSONObject json = new JSONObject();
    json.put("type", "interval");
    json.put("totalTime", interval.getTimeInterval());
    if (interval.getInitialTime() != null) {
      json.put("initialTime", interval.getInitialTime());
    } else {
      json.put("initialTime", JSONObject.NULL);
    }

    if (interval.getFinalTime() != null) {
      json.put("finalTime", interval.getFinalTime());
    } else {
      json.put("finalTime", JSONObject.NULL);
    }

    json.put("active", interval.getActive());
    this.jsonTree = json;
  }

  /**
   * Function that writes the tree of Activities.
   */
  public void writeDocument() {
    try {
      this.textEditor.write(this.jsonTree.toString());
      this.textEditor.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
