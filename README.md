esclient
========

Elastic Search Client for Scala

	package esclient
	
	import scala.concurrent.Await
	import scala.concurrent.Future
	import scala.concurrent.duration.DurationInt
	
	import org.elasticsearch.action.index.IndexRequest
	import org.elasticsearch.action.index.IndexResponse
	import org.elasticsearch.client.Client
	import org.scalastuff.esclient.ESClient
	import org.elasticsearch.node.NodeBuilder.nodeBuilder;
	
	object TestES extends App {
	
	  val client : Client = 
	    nodeBuilder.node.client
	  
	  val response : Future[IndexResponse] = 
	    client(new IndexRequest) 
	      
	  println("Document id: " + Await.result(response, 5 seconds).id)
	}
