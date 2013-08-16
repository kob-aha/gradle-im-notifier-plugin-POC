package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import com.ka.gradle.plugin.notifier.NotifierTask

class IMNotifierPlugin implements Plugin<Project> {
	
	def notifierTaskName = 'imNotifier'
	
	void apply(Project project) {

		project.extensions.create('imNotifier', IMNotifierPluginExtension)
		
		// Create a default notifier task that wil be used by notify rule
		Map<String, ?> taskProps = [type: NotifierTask, descritpion: 'Sends a notification message using GTalk']
		project.task(taskProps, notifierTaskName)
				
		createNotifyRule(project)
		loadDefaultParams(project)
	}
	
	// Automatically load all properties in the plugin configuration file if one 
	// exists in rootProject folder
	void loadDefaultParams(Project project) {
		
		File configFile = new File("${project.rootProject.rootDir}/im-notifier.properties")
		
		if (configFile.exists()) {
			def config = new ConfigSlurper().parse(configFile.toURI().toURL())
			project.imNotifier.senderMail = config.sender.mail
			project.imNotifier.senderPass = config.sender.password
			project.imNotifier.recipient = config.recipient
		}
	}
	
	void createNotifyRule(Project project) {
		project.tasks.addRule('Pattern: notify<TaskName>: Executes a task with the given name and sends a notification ' +
			'message with the execution result (success or failure).') { String taskName ->
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