package org.scalastuff.esclient

import scala.concurrent.Future
import scala.concurrent.Promise
import org.elasticsearch.action.Action
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.ActionRequestBuilder
import org.elasticsearch.action.ActionResponse
import org.elasticsearch.action.admin.cluster.ClusterAction
import org.elasticsearch.action.admin.cluster.health.ClusterHealthAction
import org.elasticsearch.action.admin.cluster.node.hotthreads.NodesHotThreadsAction
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoAction
import org.elasticsearch.action.admin.cluster.node.restart.NodesRestartAction
import org.elasticsearch.action.admin.cluster.node.shutdown.NodesShutdownAction
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsAction
import org.elasticsearch.action.admin.cluster.reroute.ClusterRerouteAction
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsAction
import org.elasticsearch.action.admin.cluster.state.ClusterStateAction
import org.elasticsearch.action.bulk.BulkAction
import org.elasticsearch.action.count.CountAction
import org.elasticsearch.action.delete.DeleteAction
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction
import org.elasticsearch.action.explain.ExplainAction
import org.elasticsearch.action.get.GetAction
import org.elasticsearch.action.get.MultiGetAction
import org.elasticsearch.action.index.IndexAction
import org.elasticsearch.action.mlt.MoreLikeThisAction
import org.elasticsearch.action.percolate.PercolateAction
import org.elasticsearch.action.search.MultiSearchAction
import org.elasticsearch.action.search.SearchAction
import org.elasticsearch.action.search.SearchScrollAction
import org.elasticsearch.action.update.UpdateAction
import org.elasticsearch.client.Client
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction
import org.elasticsearch.action.admin.indices.IndicesAction
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheAction
import org.elasticsearch.action.admin.indices.close.CloseIndexAction
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateAction
import org.elasticsearch.action.admin.indices.create.CreateIndexAction
import org.elasticsearch.action.admin.indices.delete.DeleteIndexAction
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingAction
import org.elasticsearch.action.admin.indices.flush.FlushAction
import org.elasticsearch.action.admin.indices.warmer.delete.DeleteWarmerAction
import org.elasticsearch.action.admin.indices.gateway.snapshot.GatewaySnapshotAction
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesAction
import org.elasticsearch.action.admin.indices.stats.IndicesStatsAction
import org.elasticsearch.action.admin.indices.segments.IndicesSegmentsAction
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsAction
import org.elasticsearch.action.admin.indices.status.IndicesStatusAction
import org.elasticsearch.action.admin.indices.refresh.RefreshAction
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsAction
import org.elasticsearch.action.admin.indices.settings.UpdateSettingsAction
import org.elasticsearch.action.admin.indices.open.OpenIndexAction
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateAction
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingAction
import org.elasticsearch.action.admin.indices.warmer.put.PutWarmerAction
import org.elasticsearch.action.admin.indices.optimize.OptimizeAction
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryAction

trait ActionMagnet[Request, Response] {
	def execute(client: Client, request: Request): Future[Response]
}

object ActionMagnet {

	implicit val bulkAction = magnet(_.bulk)
	implicit val countAction = magnet(_.count)
	implicit val deleteAction = magnet(_.delete)
	implicit val deleteByQueryAction = magnet(_.deleteByQuery)
	implicit val explainAction = magnet(_.explain)
	implicit val getAction = magnet(_.get)
	implicit val indexAction = magnet(_.index)
	implicit val moreLikeThisAction = magnet(_.moreLikeThis)
	implicit val multiGetAction = magnet(_.multiGet)
	implicit val multiSearchAction = magnet(_.multiSearch)
	implicit val percolateAction = magnet(_.percolate)
	implicit val searchAction = magnet(_.search)
	implicit val searchScrollAction = magnet(_.searchScroll)
	implicit val updateAction = magnet(_.update)

	implicit val clusterHealthAction = magnet(_.admin.cluster.health)
	implicit val clusterRerouteAction = magnet(_.admin.cluster.reroute)
	implicit val clusterStateAction = magnet(_.admin.cluster.state)
	implicit val clusterUpdateSettingsAction = magnet(_.admin.cluster.updateSettings)
	implicit val nodesHotThreadsAction = magnet(_.admin.cluster.nodesHotThreads)
	implicit val nodesInfoAction = magnet(_.admin.cluster.nodesInfo)
	implicit val nodesRestartAction = magnet(_.admin.cluster.nodesRestart)
	implicit val nodesShutdownAction = magnet(_.admin.cluster.nodesShutdown)
	implicit val nodesStatsAction = magnet(_.admin.cluster.nodesStats)

	implicit val analyzeAction = magnet(_.admin.indices.analyze)
	implicit val clearIndicesCacheAction = magnet(_.admin.indices.clearCache)
	implicit val closeIndexAction = magnet(_.admin.indices.close)
	implicit val createIndexAction = magnet(_.admin.indices.create)
	implicit val deleteIndexAction = magnet(_.admin.indices.delete)
	implicit val deleteIndexTemplateAction = magnet(_.admin.indices.deleteTemplate)
	implicit val deleteMappingAction = magnet(_.admin.indices.deleteMapping)
	implicit val deleteWarmerAction = magnet(_.admin.indices.deleteWarmer)
	implicit val flushAction = magnet(_.admin.indices.flush)
	implicit val gatewaySnapshotAction = magnet(_.admin.indices.gatewaySnapshot)
	implicit val indicesAliasesAction = magnet(_.admin.indices.aliases)
	implicit val indicesExistsAction = magnet(_.admin.indices.exists)
	implicit val indicesSegmentsAction = magnet(_.admin.indices.segments)
	implicit val indicesStatsAction = magnet(_.admin.indices.stats)
	implicit val indicesStatusAction = magnet(_.admin.indices.status)
	implicit val openIndexAction = magnet(_.admin.indices.open)
	implicit val optimizeAction = magnet(_.admin.indices.optimize)
	implicit val putIndexTemplateAction = magnet(_.admin.indices.putTemplate)
	implicit val putMappingAction = magnet(_.admin.indices.putMapping)
	implicit val putWarmerAction = magnet(_.admin.indices.putWarmer)
	implicit val refreshAction = magnet(_.admin.indices.refresh)
	implicit val typesExistsAction = magnet(_.admin.indices.typesExists)
	implicit val updateSettingsAction = magnet(_.admin.indices.updateSettings)
	implicit val validateQueryAction = magnet(_.admin.indices.validateQuery)

	private def magnet[Request <: ActionRequest[Request], Response <: ActionResponse](action: Client => (Request, ActionListener[Response]) => Unit): ActionMagnet[Request, Response] =
		new ActionMagnet[Request, Response] {
			def execute(client: Client, request: Request) = {
				val promise = Promise[Response]()
				action(client)(request, actionListener(promise))
				promise.future
			}
		}

}
