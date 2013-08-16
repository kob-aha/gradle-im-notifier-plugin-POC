package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import com.ka.gradle.plugin.notifier.NotifierTask

class IMNotifierPlugin implements Plugin<Project> {
	
	def notifierTaskName = 'imNotifier'
	
	void apply(Project project) {

		// Create a default notifier task that wil be used by notify rule
		Map<String, ?> taskProps = [type: NotifierTask, descritpion: 'Sends a notification message using GTalk']
		project.task(taskProps, notifierTaskName)
				
		createNotifyRule(project)
	}
	
	void createNotifyRule(Project project) {
		project.tasks.addRule('Pattern: notify<TaskName>: Executes a task with the given name and sends a notification ' +
			'message with the execution result (success or failure)') { String taskName ->
			final String prefix = "notify"
			if (taskName.startsWith(prefix) && !project.tasks.findByName(taskName)) {
				def targetTaskName = taskName - prefix
				def taskTaskNameUncapitalize = targetTaskName[0].toLowerCase() + targetTaskName[1..-1]
				
				Task notifiedTask = project.tasks.findByName(taskTaskNameUncapitalize)
				Task notifierTask = project.tasks.findByName(notifierTaskName)
				
				if (notifiedTask && notifierTask) {
				
					notifierTask.notifiedTask = notifiedTask
					notifiedTask.finalizedBy = [notifierTaskName]
				
					project.task(taskName) {
						dependsOn = [notifiedTask]
					}
				}
			}
		}
	}
}