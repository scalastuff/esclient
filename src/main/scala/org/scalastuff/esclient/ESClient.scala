package org.scalastuff

import scala.concurrent.Future
import scala.concurrent.Promise
import org.elasticsearch.client.Client
import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.ActionResponse
import org.elasticsearch.action.ListenableActionFuture
import org.elasticsearch.action.ActionListener

package object esclient {

	implicit class ESClient(val javaClient: Client) extends AnyVal {
		def execute[Request, Response](request: Request)(implicit action: ActionMagnet[Request, Response]): Future[Response] =
				action.execute(javaClient, request)
	}
	
	implicit class ESListenableActionFuture[Response](laf: ListenableActionFuture[Response]) {
		def future: Future[Response] = {
			val promise = Promise[Response]()
			laf.addListener(actionListener(promise))
			promise.future
		}
	}

	private[esclient] def actionListener[A](promise: Promise[A]) = new ActionListener[A] {
		def onResponse(response: A) {
			promise.success(response)
		}
		def onFailure(e: Throwable) {
			promise.failure(e)
		}
	}

}


