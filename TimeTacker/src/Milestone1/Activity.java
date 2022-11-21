package Milestone1;/* Activity class is designed to implement the Composite Pattern.
 * Tasks and Projects are Activities.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Activity {
  protected String name;
  protected LocalDateTime initialTime;
  protected long totalTime;

  protected Clock clock;
  protected LocalDateTime finalTime;

  // Father will be always a Project instance. If project is Root, father must be NULL.
  protected Project father;

  protected ArrayList<String> tags;

  private Logger logger = LoggerFactory.getLogger(Activity.class);

  public Activity(String name, Project father) {
    this.clock = Clock.getInstance();
    this.name = name;
    this.father = father;
    this.totalTime = 0;
    this.initialTime = null;
    this.tags = new ArrayList<>();

    logger.debug("Activity " + this.getName() + " created");
    logger.trace("Tags size: 0");
  }

  public abstract Activity getFather();

  public abstract void setName(String name);

  public abstract void setInitialTime(LocalDateTime time);

  public abstract void setTotalTime(long totalTime);

  public abstract void setFinalTime(LocalDateTime time);

  public abstract void setFather(Project father);

  public abstract String getName();

  public abstract long getTotalTime();

  public abstract LocalDateTime getFinalTime();

  public abstract LocalDateTime getInitialTime();

  public abstract void acceptVisitor(Visitor visitor);

  public abstract void setFinalAndTotalTime(LocalDateTime time, long seconds);

  public abstract void addTag(String tag);

  public abstract void start();

}