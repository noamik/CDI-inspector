package cz.muni.fi.cdii.eclipse.inspection;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.jboss.tools.cdi.core.CDICoreNature;
import org.jboss.tools.cdi.core.CDIUtil;
import org.jboss.tools.cdi.core.ICDIProject;

import cz.muni.fi.cdii.common.model.Model;
import cz.muni.fi.cdii.eclipse.Activator;

/**
 * Eclipse fashion thread encapsulation of local project cdi metadata extraction.
 *
 */
public class LocalInspectionJob extends Job {
	
    @Inject
	private IEventBroker broker;
    
    @Inject
    private IEclipseContext context;
    
	private final IProject project;

	public LocalInspectionJob(IProject project, IEclipseContext context) {
		super("Inspectiong local CDI project: " + project.getName());
		this.project = project;
        ContextInjectionFactory.inject(this, context);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			return checkedRun();
		} catch (RuntimeException ex) {
			return new Status(Status.ERROR, Activator.PLUGIN_ID, "Local inspection failed", 
					ex);
		}
	}

	public IStatus checkedRun() {
		ICDIProject cdiProject = LocalInspectionJob.getCdiProjectFromProject(this.project);
		Model model = LocalCdiInspector.inspect(cdiProject);
		InspectionTask task = new LocalInspectionTask(this.project, this.context);
		return Utils.createGraphInspectionAndDispatch(model, task, this.broker);
	}

	private static ICDIProject getCdiProjectFromProject(IProject project) {
		CDICoreNature nature = CDIUtil.getCDINatureWithProgress(project);
		ICDIProject cdiProject = nature.getDelegate();
		return cdiProject;
	}
}