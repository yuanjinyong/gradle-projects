package ${archetype.project.pkg};

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;

import ${archetype.project.pkg}.task.${archetype.className-'Plugin'}Task;

public class ${archetype.className} implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        // 引用groovy扩展变量
        final ${archetype.className}Convention pluginConvention = new ${archetype.className}Convention(project);
        project.getConvention().getPlugins().put("${archetype.className-'Plugin'}", pluginConvention);

        // 创建hello任务
        project.getTasks().add("hello", ${archetype.className-'Plugin'}Task.class);

        // 创建copyAll任务
        Copy copyTask = project.getTasks().add("copyAll", Copy.class);
        copyTask.dependsOn(project.getPath() + ":jar");
        copyTask.from(project.getConfigurations().getByName("runtime"));
        copyTask.from(project.getBuildDir().getPath() + "/classes/main");
        copyTask.from(project.getBuildDir().getPath() + "/classes/resources");
        copyTask.into(project.getBuildDir().getPath() + "/dist");
    }

}